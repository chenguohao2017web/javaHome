package com.mycomp.home.entity;

import lombok.Data;

@Data
public class UserInfo extends User{

    private String roomNum;
    private Integer price;
}
