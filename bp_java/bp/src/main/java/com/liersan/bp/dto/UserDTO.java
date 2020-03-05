package com.liersan.bp.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 向前端返回的用户信息 , 不包含敏感内容
 */
@Data
public class UserDTO{
    private String uId;
    private String username;
    private String imgUrl;
    private String account;
    private Integer status;
    private Integer column;
    private Integer article;
    private Integer note;
}
