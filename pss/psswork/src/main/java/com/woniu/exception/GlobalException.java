package com.woniu.exception;


import com.woniu.util.ResponseResult;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpServerErrorException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(NumberFormatException.class)
    @ResponseBody
    public ResponseResult<Void> getNumberFormatException(NumberFormatException ex){
        return new ResponseResult<>(4003,"转换数值类型出错!"+ex.getMessage());
    }

    /**
     * 400 - Bad Request
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public ResponseResult<Void> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return new ResponseResult<>(400,"参数解析失败"+ex.getMessage());
    }

    /**
     * 405 - Method Not Allowed
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public ResponseResult<Void> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        return new ResponseResult<>(405,"不支持当前请求的方法"+ex.getMessage());
    }

    /**
     * 415 - Unsupported Media Type
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseBody
    public ResponseResult<Void> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex) {
        return new ResponseResult<>(415,"不支持当前媒体类型"+ex.getMessage());
    }

    /**
     * 500 - Internal Server Error
     */
    @ExceptionHandler(HttpServerErrorException.class)
    @ResponseBody
    public ResponseResult<Void> handleServerErrorException(HttpServerErrorException ex) {
        return new ResponseResult<>(500,"服务器异常"+ex.getMessage());
    }

    @ExceptionHandler(ValidatorException.class)
    @ResponseBody
    public ResponseResult<Object> handleValidatorException(ValidatorException e) {
        //取出BindingResult中的异常信息并保存到错误对象中返回给页面
        Map<String,String> beanError=new HashMap<String,String>();
        List<FieldError> list = e.getResult().getFieldErrors();
        for (FieldError error : list) {
            beanError.put(error.getField(),error.getDefaultMessage());
        }
        //将错误对象发送到页面
        return new ResponseResult<>(416,"表单不符合验证规则",beanError);
    }

}
