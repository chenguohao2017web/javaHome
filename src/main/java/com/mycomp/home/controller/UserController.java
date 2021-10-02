package com.mycomp.home.controller;

import com.mycomp.home.common.Result;
import com.mycomp.home.entity.User;
import com.mycomp.home.mapper.UserMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/getAll")
    public Result getUsersAll() {
        List<User> users = userMapper.selectList(null);
        return Result.ok(users);
    }
}
