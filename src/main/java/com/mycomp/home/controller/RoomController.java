package com.mycomp.home.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mycomp.home.common.Result;
import com.mycomp.home.entity.Room;
import com.mycomp.home.entity.User;
import com.mycomp.home.mapper.RoomMapper;
import com.mycomp.home.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
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
        QueryWrapper<Room> roomQueryWrapper = new QueryWrapper<>();
        roomQueryWrapper.orderByAsc("seq");
        IPage<Room> iPage1 = roomMapper.selectPage(iPage, roomQueryWrapper);
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

        //根据房间号查询房间
        Room room = roomMapper.selectOne(new QueryWrapper<Room>().lambda().eq(Room::getRoomNum, roomNum));
        if(room != null) {
            return Result.fail("房间号已存在");
        }

        Integer price = jsonObject.getInteger("price");
        Room newRoom = new Room(roomNum, price);
        roomMapper.insert(newRoom);
        return Result.ok(newRoom.getId());
    }

    @PostMapping("/del")
    public Result del(@RequestBody JSONObject jsonObject) {
        Integer id = jsonObject.getInteger("id");
        // 判断该房间是否绑定用户，如果是，不允许删除
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getRoomId, id);
        User user = userMapper.selectOne(queryWrapper);
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
