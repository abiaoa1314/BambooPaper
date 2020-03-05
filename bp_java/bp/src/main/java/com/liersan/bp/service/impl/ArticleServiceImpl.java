package com.liersan.bp.service.impl;

import com.liersan.bp.dto.ArticleDTO;
import com.liersan.bp.dto.UserDTO;
import com.liersan.bp.entity.Article;
import com.liersan.bp.entity.Column;
import com.liersan.bp.entity.User;
import com.liersan.bp.mapper.ArticleMapper;
import com.liersan.bp.mapper.ColumnMapper;
import com.liersan.bp.mapper.UserMapper;
import com.liersan.bp.service.ArticleService;
import com.liersan.bp.utils.entity.StatusCode;
import com.liersan.bp.utils.exception.BpException;
import com.liersan.bp.utils.utils.BeanHelper;
import com.liersan.bp.utils.utils.JsonUtils;
import com.liersan.bp.utils.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 文章service实现类
 */
@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private ColumnMapper columnMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * 通过专栏id查找此专栏下的所有文章
     * @param cId 专栏id
     * @return 此专栏下的所有文章
     */
    @Override
    public List<ArticleDTO> getAllArticleByCId(String cId) {
        Article article = new Article();
        article.setCId(cId);
        // 通过cid查找出此专栏下的所有文章
        List<Article> articleList = articleMapper.select(article);
        if (articleList.size() == 0)
            throw new BpException(StatusCode.ArticleIsNull);
        // 转换成dto对象 , 返回给
        List<ArticleDTO> articleDTOList = BeanHelper.copyWithCollection(articleList, ArticleDTO.class);
        return articleDTOList;
    }

    /**
     * 通过文章id获取到一个文章的信息
     * @param aId 文章id
     * @return 返回文章的dto对象
     */
    @Override
    public ArticleDTO getArticleByAId(String aId) {
        Article article = articleMapper.selectByPrimaryKey(aId);
        ArticleDTO articleDTO = BeanHelper.copyProperties(article, ArticleDTO.class);
        return articleDTO;
    }

    /**
     * 通过文章的id获取文章的内容
     * @param aId 文章的id
     * @return 返回文章的内容
     */
    @Override
    public String getContentByAId(String aId) {
        // 首先查找此文章
        Article article = articleMapper.selectByPrimaryKey(aId);
        // 获取文章内容
        String content = article.getContent();
        return content;
    }

    /**
     * 在此专栏id下方插入一篇文章
     * @param cId 专栏id
     * @param map 其中包含文章的标题,简介,内容
     * @return 返回修改过的用户dto对象
     */
    @Override
    @Transactional
    public UserDTO saveArticleByCId(String cId, Map<String, String> map) {
        // 首先新增文章
        Article article = new Article();
        article.setAId(UUIDUtils.UUID());
        article.setCId(cId);
        article.setTime(new Date());
        article.setTitle(map.get("title"));
        article.setSynopsis(map.get("synopsis"));
        article.setContent(map.get("content"));

        // 在查找cid对应的专栏信息
        Column column = columnMapper.selectByPrimaryKey(cId);
        // 对文章进行用户id的赋值
        article.setUId(column.getUId());
        // 保存进数据库
        int count = articleMapper.insert(article);
        if (count != 1)
            throw new BpException(StatusCode.SaveArticleError);
        // 对应专栏的文章数量加1
        column.setArticle(column.getArticle() + 1);
        // 保存进数据库
        count = columnMapper.updateByPrimaryKey(column);
        if (count != 1)
            throw new BpException(StatusCode.SaveArticleError);
        // 查找此用户 , 对这个用户的文章数量进行改变
        User user = userMapper.selectByPrimaryKey(column.getUId());
        // 对文章数量进行加1
        user.setArticle(user.getArticle() + 1);
        // 保存进数据库
        count = userMapper.updateByPrimaryKey(user);
        if (count != 1)
            throw new BpException(StatusCode.SaveArticleError);
        // 把用户信息转换成dto对象 , 返回给前端
        UserDTO userDTO = BeanHelper.copyProperties(user, UserDTO.class);
        return userDTO;
    }

    /**
     * 通过文章id修改一篇文章
     * @param aId 文章id
     * @param map 其中包含文章的标题,简介,内容
     */
    @Override
    @Transactional
    public void alterArticleByAId(String aId, Map<String, String> map) {
        Article article = articleMapper.selectByPrimaryKey(aId);
        article.setTitle(map.get("title"));
        article.setSynopsis(map.get("synopsis"));
        article.setContent(map.get("content"));
        // 在保存进数据库
        int count = articleMapper.updateByPrimaryKey(article);
        if (count != 1)
            throw new BpException(StatusCode.AlterArticleError);
    }

    /**
     * 通过文章id来删除一篇文章
     * @param aId 文章id
     * @return 返回更新后的用户dto对象
     */
    @Override
    @Transactional
    public UserDTO deleteArticleByAId(String aId) {
        //首先通过文章id查找对应的文章
        Article article = articleMapper.selectByPrimaryKey(aId);
        //在删除对应的文章
        int count = articleMapper.deleteByPrimaryKey(aId);
        if (count != 1)
            throw new BpException(StatusCode.DeleteArticleError);
        // 查找对应的专栏信息 , 进行减1
        Column column = columnMapper.selectByPrimaryKey(article.getCId());
        column.setArticle(column.getArticle() - 1);
        // 对数据库进行更新
        count = columnMapper.updateByPrimaryKey(column);
        if (count != 1)
            throw new BpException(StatusCode.DeleteArticleError);
        // 查找对应的用户信息 , 对文章进行减1
        User user = userMapper.selectByPrimaryKey(article.getUId());
        user.setArticle(user.getArticle() - 1);
        // 对数据库进行更新
        count = userMapper.updateByPrimaryKey(user);
        if (count != 1)
            throw new BpException(StatusCode.DeleteArticleError);
        // 用户转换成dto对象 , 返回前端
        UserDTO userDTO = BeanHelper.copyProperties(user, UserDTO.class);
        return userDTO;
    }

    /**
     * 通过专栏id和文章标题来搜索文章
     * @param cId 专栏id
     * @param title 文章标题检索关键字
     * @return 返回查找到的dto集合
     */
    @Override
    public List<ArticleDTO> searchArticle(String cId, String title) {
        if (title.equals("null"))
            title = "";
        Example example = new Example(Article.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("cId",cId);
        criteria.andLike("title","%" + title + "%");
        List<Article> articleList = articleMapper.selectByExample(example);
        // 转换成dto对象
        List<ArticleDTO> articleDTOList = BeanHelper.copyWithCollection(articleList, ArticleDTO.class);
        return articleDTOList;
    }
}
