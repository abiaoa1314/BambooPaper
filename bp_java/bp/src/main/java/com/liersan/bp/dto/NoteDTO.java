package com.liersan.bp.dto;
import lombok.Data;
import java.util.Date;

@Data
public class NoteDTO {
    private String nId;
    private String title;
    private String synopsis;
    private Date time;
}
