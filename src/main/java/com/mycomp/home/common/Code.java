package com.mycomp.home.common;

public enum Code {

    SUCCESS("操作成功", 200);

    private String msg;
    private Integer num;

    private Code(String msg, Integer num) {
        this.msg = msg;
        this.num = num;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
