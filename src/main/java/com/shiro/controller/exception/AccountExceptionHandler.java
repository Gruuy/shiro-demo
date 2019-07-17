package com.shiro.controller.exception;

import org.apache.shiro.authc.AccountException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 异常处理控制器
 * @author Gruuy
 * @date 2019-7-17
 * 该注解表示拦截异常
 */
@RestControllerAdvice
public class AccountExceptionHandler {
    /**
     * 捕捉CustomerRealm的异常
     */
    @ExceptionHandler(AccountException.class)
    public String handleShiroException(Exception ex){
        return ex.getMessage();
    }
}
