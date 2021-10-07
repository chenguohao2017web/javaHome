package com.mycomp.home.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mycomp.home.entity.User;
import com.mycomp.home.entity.UserInfo;

import java.util.List;
import java.util.Map;

public interface UserMapper extends BaseMapper<User> {

    List<UserInfo> getUserWithRoomList(Map pageParams);
}
