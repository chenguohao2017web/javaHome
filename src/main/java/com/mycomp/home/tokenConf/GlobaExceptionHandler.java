package com.mycomp.home.tokenConf;

import com.mycomp.home.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobaExceptionHandler {

    @ExceptionHandler(value = TokenAuthExpiredException.class)
    @ResponseBody
    public Result tokenExpired() {
        return Result.fail("token异常/已过期");
    }
}
