package com.mycomp.home.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.mycomp.home.common.Result;
import com.mycomp.home.entity.Room;
import com.mycomp.home.entity.User;
import com.mycomp.home.mapper.RoomMapper;
import com.mycomp.home.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    private UserMapper userMapper;

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
