package com.mycomp.home.common;

import lombok.Data;

@Data
public class Result {

    private Integer code;
    private Object data;
    private String msg;

    public Result(Integer code, Object data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public static Result ok(Object data) {
        return new Result(200, data, "succful");
    }

    public static Result ok() {
        return new Result(200, "", "succful");
    }

    public static Result fail() {
        return new Result(500, "", "fail");
    }

    public static Result fail(String msg) {
        return new Result(500, "", msg);
    }

}
