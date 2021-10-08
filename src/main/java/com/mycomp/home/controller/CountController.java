package com.mycomp.home.controller;


import com.alibaba.fastjson.JSONObject;
import com.mycomp.home.common.Result;
import com.mycomp.home.entity.Count;
import com.mycomp.home.mapper.CountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/count")
public class CountController {

    @Autowired
    private CountMapper countMapper;

    @PostMapping("/select")
    public Result select(@RequestBody Map<String, Object> map) {
        List<Count> countListWithUserAndRoom = countMapper.getCountListWithUserAndRoom(map);
        return Result.ok(countListWithUserAndRoom);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Count count) {
        countMapper.insert(count);
        return Result.ok();
    }

    @PostMapping("/update")
    public Result update(@RequestBody Count count) {
        countMapper.updateById(count);
        return Result.ok();
    }

    @PostMapping("/del")
    public Result del(@RequestBody Count count) {
        countMapper.deleteById(count.getId());
        return Result.ok();
    }
}
