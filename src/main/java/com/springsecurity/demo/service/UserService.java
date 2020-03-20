package com.springsecurity.demo.service;

import com.springsecurity.demo.bean.User;
import com.springsecurity.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("进入service,用户名为 : "  + username);
        if(username == null || username == ""){
            throw new UsernameNotFoundException("请输入用户名!");
        }
        List<SimpleGrantedAuthority>list = new ArrayList<>();
        User user = userMapper.getUser(username);
        System.out.println(user.getUsername()+user.getPassword()+user.getRole());
        for(String s : user.getRole().split(" ")){
            s = "ROLE_" + s;
            list.add(new SimpleGrantedAuthority(s));        //由于不可能是空的(数据库中必须字段)
            System.out.println(s);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), list);
    }



}

