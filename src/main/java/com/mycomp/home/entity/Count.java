package com.mycomp.home.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

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

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    private User user;
    private Room room;

}
