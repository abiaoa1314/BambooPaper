package com.liersan.bp.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name = "bp_column")
@ApiModel(description = "专题实体类")
public class Column implements Serializable {
    @Id
    @KeySql(useGeneratedKeys = true)
    @ApiModelProperty(value = "专栏id")
    private String cId;

    @ApiModelProperty(value = "专栏标题")
    private String title;

    @ApiModelProperty(value = "专栏简介")
    private String synopsis;

    @ApiModelProperty(value = "文章数量")
    private Integer article;

    @ApiModelProperty(value = "用户id")
    private String uId;
}
