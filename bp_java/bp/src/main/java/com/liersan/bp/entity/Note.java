package com.liersan.bp.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "bp_note")
@ApiModel(description = "随笔实体类")
public class Note implements Serializable {
    @Id
    @KeySql(useGeneratedKeys = true)
    @ApiModelProperty(value = "随笔id")
    private String nId;

    @ApiModelProperty(value = "用户id")
    private String uId;

    @ApiModelProperty(value = "随笔标题")
    private String title;

    @ApiModelProperty(value = "随笔主体内容")
    private String content;

    @ApiModelProperty(value = "随笔简介")
    private String synopsis;

    @ApiModelProperty(value = "创建时间")
    private Date time;
}
