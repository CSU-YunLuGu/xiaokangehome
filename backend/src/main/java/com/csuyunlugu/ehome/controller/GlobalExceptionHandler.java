package com.csuyunlugu.ehome.controller;

import com.csuyunlugu.ehome.exception.LoginAuthException;
import com.csuyunlugu.ehome.exception.LoginException;
import com.csuyunlugu.ehome.dto.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @ClassName GlobalExceptionHandler
 * @Description TODO
 * @Author lybugproducer
 * @Date 2024/11/4 21:21
 * @Version 1.0
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 处理缺少请求参数异常
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse<Object>> handleMissingServletRequestParameterException(Exception ex) {
        String errorMessage = "缺少请求参数：" + ex.getMessage();
        ApiResponse<Object> apiResponse = ApiResponse.error(400, errorMessage);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    /**
     * 处理参数校验异常（注释声明即可）
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Object>> handleConstraintViolationException(ConstraintViolationException ex) {
        String errorMessage = ex.getMessage();
        ApiResponse<Object> apiResponse = ApiResponse.error(400, errorMessage);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    /**
     * 处理登录验证异常（自定义的异常类）
     */
    @ExceptionHandler(LoginAuthException.class)
    public ResponseEntity<ApiResponse<Object>> handleLoginAuthException(LoginAuthException ex) {
        ApiResponse<Object> apiResponse = ApiResponse.error(ex.code, ex.msg);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    /**
     * 处理登录微信后台异常（自定义的异常类）
     */
    @ExceptionHandler(LoginException.class)
    public ResponseEntity<ApiResponse<Object>> handleLoginException(LoginException ex) {
        ApiResponse<Object> apiResponse = ApiResponse.error(ex.code, ex.msg);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    /**
     * 处理请求方法不支持异常
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse<Object>> handleHttpRequestMethodNotSupportedException(Exception ex) {
        String errorMessage = "请求方法不支持：" + ex.getMessage();
        ApiResponse<Object> apiResponse = ApiResponse.error(405, errorMessage);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


    /**
     * 处理其他异常（注释声明即可）
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleException(Exception ex) {
        String errorMessage = ex.getMessage();
        ApiResponse<Object> apiResponse = ApiResponse.error(500, errorMessage);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
