package com.liersan.bp.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ArticleDTO {
    private String aId;
    private String title;
    private String synopsis;
    private Date time;
}
