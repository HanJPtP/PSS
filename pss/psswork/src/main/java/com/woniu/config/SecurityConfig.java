package com.woniu.config;

import com.woniu.filter.JwsTokenFilter;
import com.woniu.handler.*;
import com.woniu.service.impl.LoginService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //开启spring security注解
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private JwsTokenFilter jwsTokenFilter;

    @Resource
    private LoginService loginService;

    /**
     * 将PasswordEncoder注入ioc容器中
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * AuthenticationManager 认证管理器
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(loginService).passwordEncoder(passwordEncoder());
    }

    /**
     * Security配置
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 鉴权配置，访问controller中的资源时需要什么权限
        http.authorizeRequests()
                .anyRequest().authenticated();

        // 设置有哪种方式登录
        http.formLogin()
                .successHandler(new SimpleAuthenticationSuccessHandler())
                .failureHandler(new SimpleAuthenticationFailureHandler())
                .permitAll();

        // 认证和鉴权异常配置
        http.exceptionHandling()
                .authenticationEntryPoint(new SimpleAuthenticationEntryPoint())
                .accessDeniedHandler(new SimpleAccessDeniedHandler());

        // 登录退出成功后的处理器
        http.logout().logoutSuccessHandler(new SimpLogoutSuccessHandler());

        //前后端项目中要禁用session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //添加jwt过滤器到security过滤器链中
        http.addFilterAfter(jwsTokenFilter, UsernamePasswordAuthenticationFilter.class);

        // 固定写法
        http.csrf().disable();
    }
}
