package com.liersan.bp.utils.entity;

/**
 * 约定状态码
 */
public class StatusCode {
    private String code;
    private String message;

    // 提供私有构造方法
    private StatusCode() {
    }

    private StatusCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    // 只提供get方法 不提供set方法
    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    // 约定状态码
    public static StatusCode Success = new StatusCode("200","操作成功");

    public static StatusCode InvalidPhone = new StatusCode("401","无效的手机号");
    public static StatusCode InvalidPassword = new StatusCode("401","无效的密码,以字母开头,长度在6~18之间,只能包含字母、数字和下划线");
    public static StatusCode InvalidUserReset = new StatusCode("401","手机号或密码输入错误");
    public static StatusCode InvalidUsername = new StatusCode("401","无效的昵称,昵称应在1-15位之间");
    public static StatusCode InvalidImageType = new StatusCode("401","无效图片类型,支持jpg/jpeg/png文件类型");
    public static StatusCode InvalidUId = new StatusCode("401","无效的用户ID");

    public static StatusCode NoteIsNull = new StatusCode("402","随笔为空");
    public static StatusCode ColumnIsNull = new StatusCode("402","专栏为空");
    public static StatusCode ArticleIsNull = new StatusCode("402","文章为空");

    public static StatusCode Failure = new StatusCode("500","操作失败");

    public static StatusCode UserRegisterFailure = new StatusCode("501","用户注册失败");
    public static StatusCode FileUploadError = new StatusCode("501","图片上传失败");
    public static StatusCode SaveNoteError = new StatusCode("501","保存随笔失败");
    public static StatusCode SaveColumnError = new StatusCode("501","保存专栏失败");
    public static StatusCode AlterColumnError = new StatusCode("501","修改专栏失败");
    public static StatusCode SaveArticleError = new StatusCode("501","保存文章失败");
    public static StatusCode AlterArticleError = new StatusCode("501","修改文章失败");
    public static StatusCode DeleteArticleError = new StatusCode("501","删除文章失败");
}
