package com.springsecurity.demo.conf;

import com.springsecurity.demo.service.UserService;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private UserService userService;


//    链式编程
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        首页所有人可以访问，但是功能页只有对应有权限的人才能访问
//        请求授权的规则
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/level1/**").hasRole("vip1")
                .antMatchers("/level2/**").hasRole("vip2")
                .antMatchers("/level3/**").hasRole("vip3");

//        没有权限默认会跳到登录页面
        http.formLogin();
//        定制登录页
        http.formLogin().loginPage("/toLogin").loginProcessingUrl("/login");
//        注销 开启了注销功能
        http.logout().logoutSuccessUrl("/");
    }
////认证的规则
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
//                .withUser("wgr").password(new BCryptPasswordEncoder().encode("123456")).roles("vip2","vip3")
//                .and()
//                .withUser("root").password(new BCryptPasswordEncoder().encode("123456")).roles("vip1","vip2","vip3")
//                .and()
//                .withUser("guest").password(new BCryptPasswordEncoder().encode("123456")).roles("vip1");
//
//
//    }
//定义认证规则
@Override
protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());;
}

}
