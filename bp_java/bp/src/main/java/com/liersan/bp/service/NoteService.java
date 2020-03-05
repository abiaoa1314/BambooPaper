package com.liersan.bp.service;

import com.liersan.bp.dto.NoteDTO;
import com.liersan.bp.dto.UserDTO;

import java.util.List;
import java.util.Map;

/**
 * 随笔service层接口
 */
public interface NoteService {
    /**
     * 通过uid获取此用户id下的所有随笔信息
     * @param uId 用户id
     * @return 返回note的dto对象的集合
     */
    List<NoteDTO> getAllNotesByUId(String uId);

    /**
     * 根据标题进行检索
     * @param title 标题关键字
     * @return 返回随笔dto信息
     */
    List<NoteDTO> searchByTitle(String title);

    /**
     * 通过笔记id查找笔记内容
     * @param nId 笔记id
     * @return 笔记内容
     */
    String noteContentByUId(String nId);

    /**
     * 通过用户id来保存笔记
     * @param uId 用户id
     * @param map 笔记内容 , 其中有标题,简介,内容
     * @return 返回修改后的用户dto对象
     */
    UserDTO saveNote(String uId, Map<String, String> map);

    /**
     * 通过笔记id来修改笔记
     * @param nId 笔记id
     * @param map 笔记内容,其中有标题,简介,内容
     */
    void compileNote(String nId, Map<String, String> map);

    /**
     * 通过随笔id来删除随笔并向前端返回用户dto对象
     * @param nId 笔记id
     * @return 返回修改过后的用户dto对象
     */
    UserDTO deleteNote(String nId);

    /**
     * 通过笔记id来获取笔记
     * @param nId 笔记id
     * @return 笔记的dto对象
     */
    NoteDTO getNote(String nId);
}
