package com.liersan.bp.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "bp_article")
@ApiModel(description = "文章实体类")
public class Article implements Serializable {
    @Id
    @KeySql(useGeneratedKeys = true)
    @ApiModelProperty(value = "文章id")
    private String aId;

    @ApiModelProperty(value = "专栏id")
    private String cId;

    @ApiModelProperty(value = "用户id")
    private String uId;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "简介")
    private String synopsis;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "时间")
    private Date time;
}
