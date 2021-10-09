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

        //分页查询数
        String page = map.get("page").toString();
        String size = map.get("size").toString();
        map.put("page", (Integer.valueOf(page) - 1) * Integer.valueOf(size));
        List<Count> countListWithUserAndRoom = countMapper.getCountListWithUserAndRoom(map);

        //查询总数
        map.put("page",0);
        map.put("size", 99999);
        List<Count> countListWithUserAndRoom1 = countMapper.getCountListWithUserAndRoom(map);
        int total = countListWithUserAndRoom1.size();
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("list", countListWithUserAndRoom);
        stringObjectHashMap.put("total", total);
        return Result.ok(stringObjectHashMap);
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
