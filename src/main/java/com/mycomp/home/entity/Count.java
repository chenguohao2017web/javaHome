package com.mycomp.home.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Count {
    @TableId(type=IdType.AUTO)
    private Integer id;
    private Integer year;
    private Integer month;
    private Integer water;
    private Integer dian;
    private Double price;
    @TableField("user_id")
    private Integer userId;
    @TableField("room_id")
    private Integer roomId;

    private User user;
    private Room room;
}
