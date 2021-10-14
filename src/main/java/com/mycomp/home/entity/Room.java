package com.mycomp.home.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
@Data
public class Room {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("room_num")
    private String roomNum;
    private Integer price;
    private Integer seq;

    public Room() {}
    public Room(String roomNum, Integer price) {
        this.roomNum = roomNum;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(String roomNum) {
        this.roomNum = roomNum;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
