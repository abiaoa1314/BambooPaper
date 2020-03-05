package com.liersan.bp.utils.entity;

import lombok.Data;

/**
 * 封装通用返回类
 * @param <T>
 */
@Data
public class Result<T> {
    private String code;    //返回状态码
    private String message;     //返回消息
    private T data;     //返回的数据

    public Result() {
    }

    public Result(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static Result createSuccess(){
        return new Result(StatusCode.Success.getCode(),StatusCode.Success.getMessage());
    }

    public static <T> Result createSuccess(T data){
        return new Result(StatusCode.Success.getCode(),StatusCode.Success.getMessage(),data);
    }

    public static Result createFailure(StatusCode statusCode){
        return new Result(statusCode.getCode(),statusCode.getMessage());
    }
}
