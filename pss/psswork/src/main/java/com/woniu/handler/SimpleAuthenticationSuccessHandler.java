package com.woniu.handler;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.woniu.util.ResponseResult;
import com.woniu.util.TokenUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class SimpleAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        response.setContentType("application/json;charset=UTF-8");
        //获得登录人的信息对象
        User user = (User) authentication.getPrincipal();

        ResponseResult<Void> responseResult = null;
        try {
            //生成token
            String token = TokenUtil.createToken(user.getUsername());
            //如果没有报错
            responseResult = new ResponseResult<>(200, token);
        } catch (Exception e) {
            responseResult = new ResponseResult<>(2002, "生成登录令牌失败");
        }finally{
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
}
