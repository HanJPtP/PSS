package com.woniu.handler;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.woniu.util.ResponseResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class SimpLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response,
                                Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");

        ResponseResult<Void> responseResult = new ResponseResult<>(200, "退出成功");
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
