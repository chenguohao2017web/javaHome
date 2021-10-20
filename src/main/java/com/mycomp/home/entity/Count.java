package com.mycomp.home.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
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
    private Integer status;

    @TableField(fill = FieldFill.INSERT_UPDATE, update = "now()")
    private Date createTime;

    @TableField(exist = false)
    private User user;
    @TableField(exist = false)
    private Room room;
    @TableField(exist = false)
    private Count lastMonthData;

}
