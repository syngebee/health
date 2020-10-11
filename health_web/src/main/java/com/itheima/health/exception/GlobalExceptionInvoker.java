package com.itheima.health.exception;

import com.itheima.health.entity.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionInvoker {

    @ExceptionHandler
    Result handleControllerException(Exception ex) {
        ex.printStackTrace();
        return new Result(false, ex.getMessage());
    }
}