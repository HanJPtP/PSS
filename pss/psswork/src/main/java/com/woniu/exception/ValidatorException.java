package com.woniu.exception;

import org.springframework.validation.BindingResult;

public class ValidatorException extends Exception{
    //保存异常信息
    private BindingResult result;
    public BindingResult getResult() {
        return result;
    }

    public ValidatorException(BindingResult result) {
        this.result = result;
    }
}
