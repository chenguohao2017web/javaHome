package com.mycomp.home.tokenConf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Slf4j
@Component
public class AuthHandlerInterceptor implements HandlerInterceptor {
    
    @Autowired
    private TokenUtil tokenUtil;

    private long offTime = 1000 * 60 * 60 * 24; //过期时间 24小时过期
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        log.info("========进入拦截器=========");
//        if(!(handler instanceof HandlerMethod)) return true;
//
//        String token = request.getHeader("token");
//        if("null".equals(token) || null == token || "".equals(token.trim())) {
//            throw new TokenAuthExpiredException();
//        }
//
//        log.info("====token:{}", token);
//
//
//        Map<String, String> map = tokenUtil.parseToken(token);
//        String userName = map.get("userName");
//        String timestamp = map.get("timestamp");
//        System.currentTimeMillis();
//        long timeOfUse = System.currentTimeMillis() - Long.parseLong(timestamp);
//        if(timeOfUse > offTime) {
//            throw new TokenAuthExpiredException();
//        }
//
//        log.info("token正常");
        return true;
    }
}
