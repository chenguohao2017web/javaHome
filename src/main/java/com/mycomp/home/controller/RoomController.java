package com.mycomp.home.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mycomp.home.common.Result;
import com.mycomp.home.entity.Room;
import com.mycomp.home.entity.User;
import com.mycomp.home.mapper.RoomMapper;
import com.mycomp.home.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/select")
    public Result selectRoom() {
        List<Room> roomList = roomMapper.selectList(null);
        return Result.ok(roomList);

    }

    @PostMapping("/select")
    public Result selectRoomP(@RequestBody JSONObject jsonObject) {
        Integer page = jsonObject.getInteger("page");
        Integer size = jsonObject.getInteger("size");
        //分页查询
        IPage<Room> iPage = new Page<>(page, size);
        IPage<Room> iPage1 = roomMapper.selectPage(iPage, null);
        return Result.ok(iPage1);
    }

    @PostMapping("/selectById")
    public Result selectById(@RequestBody Room room){
        Room room1 = roomMapper.selectById(room.getId());
        return Result.ok(room1);
    }

    @PostMapping("/add")
    public Result add(@RequestBody JSONObject jsonObject) {

        String roomNum = jsonObject.getString("roomNum");
        Integer price = jsonObject.getInteger("price");
        roomMapper.insert(new Room(roomNum, price));
        return Result.ok();
    }

    @PostMapping("/del")
    public Result del(@RequestBody JSONObject jsonObject) {
        Integer id = jsonObject.getInteger("id");
        // 判断该房间是否绑定用户，如果是，不允许删除
        User user = userMapper.selectById(id);
        if(user != null) {
            return Result.fail("请先移除用户");
        }
        roomMapper.deleteById(id);
        return Result.ok();
    }

    @PostMapping("/update")
    public Result update(@RequestBody Room room) {
        Room selectRoom = roomMapper.selectById(room.getId());
        if(null == selectRoom) {
            return Result.fail("房间号不存在");
        }
        roomMapper.update(room, new UpdateWrapper<Room>().eq("id", room.getId()));
        return Result.ok();
    }
}
