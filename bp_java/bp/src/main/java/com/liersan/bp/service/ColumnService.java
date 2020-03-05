package com.liersan.bp.service;

import com.liersan.bp.dto.ColumnDTO;
import com.liersan.bp.dto.UserDTO;

import java.util.List;
import java.util.Map;

/**
 * 专题service层接口
 */
public interface ColumnService {
    /**
     * 获取全部专栏信息
     * @return 返回专栏信息的集合
     */
    List<ColumnDTO> getAllColumn();

    /**
     * 通过关键字查找对应的专栏信息
     * @param title 标题关键字
     * @return 返回专栏信息的dto集合
     */
    List<ColumnDTO> searchByTitle(String title);

    /**
     * 保存专栏信息
     * @param uId  用户id
     * @param map 专栏信息,其中包含标题和简介
     */
    UserDTO saveColumn(String uId , Map<String, String> map);

    /**
     * 通过专栏id来修改专栏信息
     * @param cId 专栏id
     * @param map 要修改的信息
     */
    void alterColumn(String cId, Map<String, String> map);

    /**
     * 通过专栏id删除专栏信息
     * @param cId 专栏id
     * @return 返回修改过的dto信息
     */
    UserDTO deleteColumn(String cId);
}
