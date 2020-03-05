package com.liersan.bp.utils.exception;

import com.liersan.bp.utils.entity.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 捕获抛出的异常
 */
@RestControllerAdvice
public class BaseExceptionHandler {
    @ExceptionHandler(BpException.class)
    public Result errorRuntime(BpException e){
        return new Result(e.getCode(),e.getMessage());
    }
}
