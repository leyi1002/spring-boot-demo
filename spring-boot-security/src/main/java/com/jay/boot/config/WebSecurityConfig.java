package com.jay.boot.config;

import com.jay.boot.service.security.CustomService;
import com.jay.boot.service.security.WebFilterSecurityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

/**
 * Created by Administrator on 2017/12/26.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private WebFilterSecurityInterceptor webFilterSecurityInterceptor;

    @Bean
    public UserDetailsService customUserService(){ //注册UserDetailsService 的bean
        return new CustomService();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserService()); //user Details Service验证

    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                    .anyRequest().authenticated() //任何请求,登录后可以访问
                    .and()
                .formLogin()
                    .loginPage("/login")
//                    .defaultSuccessUrl("/")
                    .failureUrl("/login?error")
                    .permitAll() //登录页面用户任意访问
                    .and()
                .logout()
                    .permitAll(); //注销行为任意访问
        http.addFilterBefore(webFilterSecurityInterceptor, FilterSecurityInterceptor.class);

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().mvcMatchers("/css/**");
    }
}
