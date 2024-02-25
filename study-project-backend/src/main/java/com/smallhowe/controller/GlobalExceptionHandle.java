package com.smallhowe.controller;

import com.smallhowe.entity.RestBean;
import jakarta.validation.ConstraintViolationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandle {

    @ExceptionHandler(ConstraintViolationException.class)
    public RestBean<String> handleValidationExceptions(){
        return RestBean.failure(400,"校验不通过");
    }
}
