/***
 * @pName mi-ocr-web-app
 * @name SpringSecurityConfiguration
 * @user HongWei
 * @date 2018/7/21
 * @desc security安全信息框架配置类
 */
package org.millions.idea.ocr.web.config;


import org.millions.idea.ocr.web.entity.config.UserProperties;
import org.millions.idea.ocr.web.config.security.PasswordEncoderEx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.dao.ReflectionSaltSource;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserProperties userProperties;


    @Autowired
    @Qualifier("UserDetailsServiceEx")
    private UserDetailsService userDetailsServiceEx;

    @Bean
    public Md5PasswordEncoder md5PasswordEncoder(){
        return new PasswordEncoderEx();
    }

    @Bean
    public ReflectionSaltSource reflectionSaltSource(){
        ReflectionSaltSource reflectionSaltSource = new ReflectionSaltSource();
        reflectionSaltSource.setUserPropertyToUse("salt");
        return reflectionSaltSource;
    }

    /**
     * 密码加密
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setSaltSource(reflectionSaltSource());
        provider.setPasswordEncoder(md5PasswordEncoder());
        provider.setUserDetailsService(userDetailsServiceEx);
        auth.authenticationProvider(provider);
    }

    /**
     * 保护机制
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(
                        userProperties.getSecurity().getLoginPage() // 登录页
                        , "/signin"   // 注册
                        , "/"   // 首页
                        , "/index"   // 首页
                        , "/api/**" // 接口
                        , "/news/**" // 新闻
                        , "/userInfo"
                ).permitAll()  // 设置无保护机制的路由或页面
                .and().authorizeRequests()    // 定义哪些路由或页面需要启用保护机制
                .anyRequest().hasRole("ADMIN")  // 任意一个请求
                .and()
                .formLogin()
                .usernameParameter("username").passwordParameter("password").permitAll()
                .loginPage(userProperties.getSecurity().getLoginPage())  // 登录入口
                .loginProcessingUrl(userProperties.getSecurity().getProcessingUrl())    // 登录验证接口
                .permitAll()
                .successForwardUrl("/user")
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                        httpServletResponse.setContentType("application/json;charset=utf-8");
                        PrintWriter out = httpServletResponse.getWriter();
                        out.write("{\"error\":0,\"msg\":\"SUCCESS\"}");
                        out.flush();
                        out.close();
                    }
                })
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                        httpServletResponse.setContentType("application/json;charset=utf-8");
                        PrintWriter out = httpServletResponse.getWriter();
                        out.write("{\"error\":1,\"msg\":\"ERROR\"}");
                        out.flush();
                        out.close();
                    }
                })
                .and().logout().permitAll();
    }

    /**
     * 排除静态资源
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/css/**")
                .antMatchers("/js/**")
                .antMatchers("/images/**")
                .antMatchers("/layui/**")
                .antMatchers("/fonts/**")
                .antMatchers("/noamd-js/**");
    }
/*
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }*/
}
