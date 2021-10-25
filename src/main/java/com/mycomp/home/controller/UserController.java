package com.mycomp.home.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mycomp.home.common.Result;
import com.mycomp.home.entity.Room;
import com.mycomp.home.entity.User;
import com.mycomp.home.entity.UserInfo;
import com.mycomp.home.mapper.RoomMapper;
import com.mycomp.home.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoomMapper roomMapper;

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

    @PostMapping("/selectUserById")
    public Result getUserById(@RequestBody User user) {
        User user1 = userMapper.selectById(user.getId());
        return Result.ok(user1);
    }

    @PostMapping("/selectUserByRoomId")
    public Result selectUserByRoomId(@RequestBody JSONObject jsonObject) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getRoomId, jsonObject.getInteger("id"));
        User user = userMapper.selectOne(queryWrapper);
        return Result.ok(user);
    }

    @PostMapping("/add")
    public Result addUser(@RequestBody User user) {
        //校验房间号是否存在
        Room room = roomMapper.selectById(user.getRoomId());
        if(room != null) {
            return Result.fail("房间号已存在");
        }
        userMapper.insert(user);
        return Result.ok();
    }

    @PostMapping("/update")
    public Result updateUser(@RequestBody User user) {
        userMapper.updateById(user);
        return Result.ok();
    }

    @PostMapping("/del")
    public Result delUser(@RequestBody User user){
        userMapper.deleteById(user.getId());
        return Result.ok();
    }

}
