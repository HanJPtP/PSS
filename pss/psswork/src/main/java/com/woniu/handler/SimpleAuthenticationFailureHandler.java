package com.woniu.handler;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.woniu.util.ResponseResult;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class SimpleAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException e) throws IOException, ServletException {


        response.setContentType("application/json;charset=UTF-8");

        ResponseResult<Void> responseResult = null;
        if(e instanceof InternalAuthenticationServiceException){
            responseResult=new ResponseResult<Void>(2001, "用户名错误");
        }else if(e instanceof BadCredentialsException){
            responseResult = new ResponseResult<Void>(2002, "密码错误");
        }else if(e instanceof LockedException){
            responseResult = new ResponseResult<Void>(2003, "账号已被锁,请联系管理员");
        }
        //转换成json对象返回出去
        //使用jacksong将对象序列化为josn字符串
        ObjectMapper mapper=new ObjectMapper();
        String json=mapper.writeValueAsString(responseResult);
        //输出json字符串到客户端
        PrintWriter printWriter = response.getWriter();
        printWriter.print(json);
        printWriter.flush();
        printWriter.close();
    }
}
