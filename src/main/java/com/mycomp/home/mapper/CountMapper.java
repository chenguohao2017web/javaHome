package com.mycomp.home.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mycomp.home.entity.Count;

import java.util.List;
import java.util.Map;

public interface CountMapper extends BaseMapper<Count> {

    List<Count> getCountListWithUserAndRoom(Map<String, Object> params);
}
