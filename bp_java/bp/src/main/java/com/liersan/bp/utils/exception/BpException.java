package com.liersan.bp.utils.exception;

import com.liersan.bp.utils.entity.Result;
import com.liersan.bp.utils.entity.StatusCode;
import lombok.Data;


/**
 * 自定义异常,封装返回的状态码和消息
 */
@Data
public class BpException extends RuntimeException{
    private String code;
    private String message;

    public BpException(StatusCode statusCode){
        this.code = statusCode.getCode();
        this.message = statusCode.getMessage();
    }
}
