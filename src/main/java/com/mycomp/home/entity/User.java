package com.mycomp.home.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class User {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String phone;
    @TableField("room_id")
    private Integer roomId;
    @TableField(fill= FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date createTime;
}
