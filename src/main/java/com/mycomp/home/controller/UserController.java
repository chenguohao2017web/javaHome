package com.mycomp.home.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mycomp.home.common.Result;
import com.mycomp.home.entity.User;
import com.mycomp.home.entity.UserInfo;
import com.mycomp.home.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/select")
    public Result getUsersAll(@RequestParam("size") Integer pageSize, @RequestParam("page") Integer page) {
        Map pageMap = new HashMap();
        pageMap.put("size", pageSize);
        pageMap.put("page", (page - 1) * pageSize);
        List<UserInfo> userWithRoomList = userMapper.getUserWithRoomList(pageMap);
        List<User> users = userMapper.selectList(null);
        Integer total = users.size();
        Map<String, Object> map = new HashMap<>();
        map.put("list", userWithRoomList);
        map.put("total", total);
        return Result.ok(map);
    }
}
