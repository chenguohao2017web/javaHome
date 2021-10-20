package com.mycomp.home.controller;


import com.alibaba.fastjson.JSONObject;
import com.mycomp.home.common.Result;
import com.mycomp.home.entity.Count;
import com.mycomp.home.mapper.CountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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

        //根据当前年月房间号查询上个月数据
//        Integer year = Integer.parseInt(map.get("year").toString());
//        Integer month = Integer.parseInt(map.get("month").toString());
//        if(month == 1) {
//            month = 12;
//            year -= 1;
//        } else {
//            month -= 1;
//        }
//        map.put("year", year);
//        map.put("month", month);
        for(Count count : countListWithUserAndRoom) {
            //根据当前年月房间号查询上个月数据
//            for(Count selectCount: lastMonthCountListWithUserAndRoom) {
//                if(count.getRoomId() == selectCount.getRoomId()) {
//                    count.setLastMonthData(selectCount);
//                }
//            }

            Integer year = count.getYear();
            Integer month = count.getMonth();
            if(month == 1) {
                month = 12;
                year -= 1;
            } else {
                month -= 1;
            }
            map.put("year", year);
            map.put("month", month);
            List<Count> lastMonthCountListWithUserAndRoom = countMapper.getCountListWithUserAndRoom(map);
            if(lastMonthCountListWithUserAndRoom.size() > 0) {
                count.setLastMonthData(lastMonthCountListWithUserAndRoom.get(0));
            }

        }
        stringObjectHashMap.put("list", countListWithUserAndRoom);
        stringObjectHashMap.put("total", total);
        return Result.ok(stringObjectHashMap);
    }

    @PostMapping("/selectById")
    public Result selectById(@RequestBody Count count) {
        Count count1 = countMapper.selectById(count.getId());
        return Result.ok(count1);
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
