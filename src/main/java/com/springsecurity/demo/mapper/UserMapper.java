package com.springsecurity.demo.mapper;

import com.springsecurity.demo.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.Mapping;

@Mapper
public interface UserMapper {
    User getUser(@Param("username") String username);
}
