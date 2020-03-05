package com.liersan.bp.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name = "bp_user")
@ApiModel(description = "用户实体类")
public class User implements Serializable {
    @Id
    @KeySql(useGeneratedKeys = true)
    @ApiModelProperty(value = "用户id")
    private String uId;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "用户密码")
    private String password;

    @ApiModelProperty(value = "用户账号(手机号)")
    private String account;

    @ApiModelProperty(value = "用户头像地址")
    private String imgUrl;

    @ApiModelProperty(value = "用户状态码 0:博主 1:访客")
    private Integer status;

    @ApiModelProperty(value = "专栏数量")
    private Integer column;

    @ApiModelProperty(value = "文章数量")
    private Integer article;

    @ApiModelProperty(value = "随笔数量")
    private Integer note;
}
