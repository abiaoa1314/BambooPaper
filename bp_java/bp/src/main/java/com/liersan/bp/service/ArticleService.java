package com.liersan.bp.service;

import com.liersan.bp.dto.ArticleDTO;
import com.liersan.bp.dto.UserDTO;

import java.util.List;
import java.util.Map;

/**
 * 文章service接口
 */
public interface ArticleService {
    /**
     * 通过专栏id查找此专栏下的所有文章
     * @param cId 专栏id
     * @return 此专栏下的所有文章
     */
    List<ArticleDTO> getAllArticleByCId(String cId);

    /**
     * 通过文章id获取到一个文章的信息
     * @param aId 文章id
     * @return 返回文章的dto对象
     */
    ArticleDTO getArticleByAId(String aId);

    /**
     * 通过文章的id获取文章的内容
     * @param aId 文章的id
     * @return 返回文章的内容
     */
    String getContentByAId(String aId);

    /**
     * 在此专栏id下方插入一篇文章
     * @param cId 专栏id
     * @param map 其中包含文章的标题,简介,内容
     * @return 返回修改过的用户dto对象
     */
    UserDTO saveArticleByCId(String cId, Map<String, String> map);

    /**
     * 通过文章id修改一篇文章
     * @param aId 文章id
     * @param map 其中包含文章的标题,简介,内容
     */
    void alterArticleByAId(String aId, Map<String, String> map);

    /**
     * 通过文章id来删除一篇文章
     * @param aId 文章id
     * @return 返回更新后的用户dto对象
     */
    UserDTO deleteArticleByAId(String aId);

    /**
     * 通过专栏id和文章标题来搜索文章
     * @param cId 专栏id
     * @param title 文章标题检索关键字
     * @return 返回查找到的dto集合
     */
    List<ArticleDTO> searchArticle(String cId, String title);
}
