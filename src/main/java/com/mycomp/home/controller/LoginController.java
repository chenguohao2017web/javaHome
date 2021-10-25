package com.mycomp.home.controller;

import com.alibaba.fastjson.JSONObject;
import com.mycomp.home.common.Result;
import com.mycomp.home.tokenConf.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private TokenUtil tokenUtil;

    @PostMapping("/login")
    public Result login(@RequestBody JSONObject jsonObject) {
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        if("admin".equals(username) && "Chen123!".equals(password)) {
            String token = tokenUtil.getToken("admin");
            return Result.ok(token);
        }
        return Result.fail("用户或密码不正确");
    }
}
