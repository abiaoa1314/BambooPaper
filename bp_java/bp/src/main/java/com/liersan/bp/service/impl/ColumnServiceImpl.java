package com.liersan.bp.service.impl;

import com.liersan.bp.dto.ColumnDTO;
import com.liersan.bp.dto.UserDTO;
import com.liersan.bp.entity.Article;
import com.liersan.bp.entity.Column;
import com.liersan.bp.entity.User;
import com.liersan.bp.mapper.ArticleMapper;
import com.liersan.bp.mapper.ColumnMapper;
import com.liersan.bp.mapper.UserMapper;
import com.liersan.bp.service.ColumnService;
import com.liersan.bp.utils.entity.StatusCode;
import com.liersan.bp.utils.exception.BpException;
import com.liersan.bp.utils.utils.BeanHelper;
import com.liersan.bp.utils.utils.JsonUtils;
import com.liersan.bp.utils.utils.UUIDUtils;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

/**
 * 专题service层实现类
 */
@Service
public class ColumnServiceImpl implements ColumnService {
    @Autowired
    private ColumnMapper columnMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ArticleMapper articleMapper;

    /**
     * 获取全部专栏信息
     * @return 返回专栏信息的集合
     */
    @Override
    public List<ColumnDTO> getAllColumn() {
        List<Column> columnList = columnMapper.selectAll();
        if (columnList.size() == 0)
            throw new BpException(StatusCode.ColumnIsNull);
        List<ColumnDTO> columnDTOList = BeanHelper.copyWithCollection(columnList, ColumnDTO.class);
        return columnDTOList;
    }

    /**
     * 通过关键字查找对应的专栏信息
     * @param title 标题关键字
     * @return 返回专栏信息的dto集合
     */
    @Override
    public List<ColumnDTO> searchByTitle(String title) {
        //进行模糊查找
        Example example = new Example(Column.class);
        Example.Criteria criteria = example.createCriteria();
        //如果前端没有传过来数据 , 则查找全部
        if (title.equals("null"))
            title = "";
        // 编写sql语句
        criteria.andLike("title","%" + title +"%");
        List<Column> columnList = columnMapper.selectByExample(example);
        // 转换成dto对象返回给前端
        List<ColumnDTO> columnDTOList = BeanHelper.copyWithCollection(columnList, ColumnDTO.class);
        return columnDTOList;
    }

    /**
     * 保存专栏信息
     * @param uId  用户id
     * @param map 专栏信息,其中包含标题和简介
     */
    @Override
    @Transactional
    public UserDTO saveColumn(String uId, Map<String, String> map) {
        Column column = new Column();
        column.setTitle(map.get("title"));
        column.setSynopsis(map.get("synopsis"));
        column.setCId(UUIDUtils.UUID());
        column.setUId(uId);
        int count = columnMapper.insertSelective(column);
        if (count != 1)
            throw new BpException(StatusCode.SaveColumnError);
        // 通过主键查找用户信息
        User user = userMapper.selectByPrimaryKey(uId);
        user.setColumn(user.getColumn() + 1);
        // 更新到数据库中
        userMapper.updateByPrimaryKey(user);
        // 转换成dto对象 , 返回给前端
        UserDTO userDTO = BeanHelper.copyProperties(user, UserDTO.class);
        return userDTO;
    }

    /**
     * 通过专栏id来修改专栏信息
     * @param cId 专栏id
     * @param map 要修改的信息
     */
    @Override
    @Transactional
    public void alterColumn(String cId, Map<String, String> map) {
        // 首先通过cid查询这个专栏信息
        Column column = columnMapper.selectByPrimaryKey(cId);
        column.setTitle(map.get("title"));
        column.setSynopsis(map.get("synopsis"));
        int count = columnMapper.updateByPrimaryKey(column);
        if (count != 1)
            throw new BpException(StatusCode.AlterColumnError);
    }

    /**
     * 通过专栏id删除专栏信息
     * @param cId 专栏id
     * @return 返回修改过的dto信息
     */
    @Override
    @Transactional
    public UserDTO deleteColumn(String cId) {
        // 首先查询这个专栏在哪个用户id下面
        Column column = columnMapper.selectByPrimaryKey(cId);
        // 查询用户
        User user = userMapper.selectByPrimaryKey(column.getUId());
        // 首先删除专栏信息
        int count = columnMapper.deleteByPrimaryKey(cId);
        // 若是结果不为1条 则抛出异常
        if (count != 1)
            throw new BpException(StatusCode.Failure);
        Article article = new Article();
        article.setCId(cId);
        // 首先查询在这个专栏下面的文章
        List<Article> articleList = articleMapper.select(article);
        // 然后在删除
        count = articleMapper.delete(article);
        // 如果删除的不等于查询的数组的长度 , 则抛出异常
        if (count != articleList.size())
            throw new BpException(StatusCode.Failure);
        // 先减去专栏的1 , 在减去文章的size
        user.setColumn(user.getColumn() - 1);
        user.setArticle(user.getArticle() - count);
        // 更新进数据库
        count = userMapper.updateByPrimaryKey(user);
        if (count != 1)
            throw new BpException(StatusCode.Failure);
        //转换成dto对象返回给前端
        UserDTO userDTO = BeanHelper.copyProperties(user, UserDTO.class);
        return userDTO;
    }
}
