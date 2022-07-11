package com.woniu.filter;

import com.woniu.service.impl.LoginService;
import com.woniu.util.TokenUtil;
import lombok.SneakyThrows;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwsTokenFilter extends OncePerRequestFilter {

    @Resource
    private LoginService loginService;

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        /**
         * 1、判断当前请求是不是登录请求，是登录请求直接放行
         */
        //获得认证对象
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        //当前是登录
        if(authentication!=null){
            //是登录请求，不用管，直接放行
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }

        /**
         * 2、不是登录请求，验证token
         */
        //获得请求头中的token  这个里面要写请求头中token的键
        String tokenStr= httpServletRequest.getHeader("rememberMe");

        //获得请求中的token,验证token是否为null
        if(StringUtils.isEmpty(tokenStr)){
            //请求中没有token，不用管
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }

        //请求中有token，但不合法
        if(!TokenUtil.verifyToken(tokenStr)){
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }

        //有token,合法，将jwtToken转为security的UsernamePasswordAuthenticationToken对象
        //获得当前用户的帐号
        String username= TokenUtil.getUserName(tokenStr);
        //调用security的UserDetailsService获得UserDetails对象
        UserDetails userDetails=loginService.loadUserByUsername(username);

        //将jwstoken转为security的token
        UsernamePasswordAuthenticationToken authenticationToken=
                new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
        //将UsernamePasswordAuthenticationToken保存到security的上下文中(公共区域)
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        //进行到下一个过滤器
        filterChain.doFilter(httpServletRequest,httpServletResponse);

    }
}
