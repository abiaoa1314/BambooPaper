package com.liersan.bp.service.impl;

import com.liersan.bp.dto.NoteDTO;
import com.liersan.bp.dto.UserDTO;
import com.liersan.bp.entity.Note;
import com.liersan.bp.entity.User;
import com.liersan.bp.mapper.NoteMapper;
import com.liersan.bp.mapper.UserMapper;
import com.liersan.bp.service.NoteService;
import com.liersan.bp.utils.entity.StatusCode;
import com.liersan.bp.utils.exception.BpException;
import com.liersan.bp.utils.utils.BeanHelper;
import com.liersan.bp.utils.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 随笔service层实现类
 */
@Service
public class NoteServiceImpl implements NoteService {
    @Autowired
    private NoteMapper noteMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * 通过uid获取此用户id下的所有随笔信息
     * @param uId 用户id
     * @return 返回note的dto对象的集合
     */
    @Override
    public List<NoteDTO> getAllNotesByUId(String uId) {
        if (StringUtils.isEmpty(uId))
            throw new BpException(StatusCode.InvalidUId);
        Note note = new Note();
        note.setUId(uId);
        //通过用户id查询到集合
        List<Note> noteList = noteMapper.select(note);
        if (noteList.size() == 0)
            throw new BpException(StatusCode.NoteIsNull);
        //转换成dto对象 , 返回给前端
        List<NoteDTO> noteDTOList = BeanHelper.copyWithCollection(noteList, NoteDTO.class);
        return noteDTOList;
    }

    /**
     * 根据标题进行检索
     * @param title 标题关键字
     * @return 返回随笔dto信息
     */
    @Override
    public List<NoteDTO> searchByTitle(String title) {
        // 进行模糊查询
        Example example = new Example(Note.class);
        Example.Criteria criteria = example.createCriteria();
        // 若是前端传过来的数据为null , 则证明前端上面也没有输入 , 这变成空字符串 , 查询全部
        if (title.equals("null"))
            title = "";
        // 编写sql语句
        criteria.andLike("title","%" + title +"%");
        List<Note> noteList = noteMapper.selectByExample(example);
        // 转换成dto对象
        List<NoteDTO> noteDTOList = BeanHelper.copyWithCollection(noteList, NoteDTO.class);
        return noteDTOList;
    }

    /**
     * 通过笔记id查找笔记内容
     * @param nId 笔记id
     * @return 笔记内容
     */
    @Override
    public String noteContentByUId(String nId) {
        Note note = noteMapper.selectByPrimaryKey(nId);
        return note.getContent();
    }

    /**
     * 通过用户id来保存笔记
     * @param uId 用户id
     * @param map 笔记内容 , 其中有标题,简介,内容
     */
    @Override
    @Transactional
    public UserDTO saveNote(String uId, Map<String, String> map) {
        Note note = new Note();
        note.setNId(UUIDUtils.UUID());
        note.setTitle(map.get("title"));
        note.setUId(uId);
        note.setSynopsis(map.get("synopsis"));
        note.setContent(map.get("content"));
        note.setTime(new Date());
        int count = noteMapper.insert(note);
        if (count != 1)
            throw new BpException(StatusCode.SaveNoteError);
        // 查找用户
        User user = userMapper.selectByPrimaryKey(uId);
        // 增加随笔数量
        user.setNote(user.getNote() + 1);
        // 在保存进数据库
        count = userMapper.updateByPrimaryKey(user);
        if (count != 1)
            throw new BpException(StatusCode.SaveNoteError);
        // 在转换成dto对象返回给前端
        UserDTO userDTO = BeanHelper.copyProperties(user, UserDTO.class);
        return userDTO;
    }

    /**
     * 通过笔记id来修改笔记
     * @param nId 笔记id
     * @param map 笔记内容,其中有标题,简介,内容
     */
    @Override
    @Transactional
    public void compileNote(String nId, Map<String, String> map) {
        Note note = noteMapper.selectByPrimaryKey(nId);
        note.setTitle(map.get("title"));
        note.setSynopsis(map.get("synopsis"));
        note.setContent(map.get("content"));
        int count = noteMapper.updateByPrimaryKeySelective(note);
        if (count != 1)
            throw new BpException(StatusCode.SaveNoteError);
    }

    /**
     * 通过随笔id来删除随笔并向前端返回用户dto对象
     * @param nId 笔记id
     * @return 返回修改过后的用户dto对象
     */
    @Override
    @Transactional
    public UserDTO deleteNote(String nId) {
        // 先通过主键查询出笔记
        Note note = noteMapper.selectByPrimaryKey(nId);
        // 在获得用户的id
        String uId = note.getUId();
        // 在通过主键删除笔记
        int count = noteMapper.deleteByPrimaryKey(nId);
        if (count != 1)
            throw new BpException(StatusCode.Failure);
        // 通过用户id查找出来用户
        User user = userMapper.selectByPrimaryKey(uId);
        // 对用户的随笔记录减1
        user.setNote(user.getNote() - 1);
        // 在对用户信息进行更新
        count = userMapper.updateByPrimaryKeySelective(user);
        if (count != 1)
            throw new BpException(StatusCode.Failure);
        // 返回给前端用户dto信息
        UserDTO userDTO = BeanHelper.copyProperties(user, UserDTO.class);
        return userDTO;
    }

    /**
     * 通过笔记id来获取笔记
     * @param nId 笔记id
     * @return 笔记的dto对象
     */
    @Override
    public NoteDTO getNote(String nId) {
        Note note = noteMapper.selectByPrimaryKey(nId);
        NoteDTO noteDTO = BeanHelper.copyProperties(note, NoteDTO.class);
        return noteDTO;
    }
}
