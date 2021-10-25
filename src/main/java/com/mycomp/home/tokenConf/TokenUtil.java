package com.mycomp.home.tokenConf;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TokenUtil {


    public String getToken(String userName) {
        String token = JWT.create()
                .withClaim("userName", userName)
                .withClaim("timestamp", System.currentTimeMillis())
                .sign(Algorithm.HMAC256("cghToken"));
        return token;
    }


    public Map<String, String> parseToken(String token) {
        HashMap<String, String> map = new HashMap<>();
        DecodedJWT decodedjwt = JWT.require(Algorithm.HMAC256("cghToken")).build()
                .verify(token);
        Claim userName = decodedjwt.getClaim("userName");
        map.put("userName", userName.asString());
        map.put("timestamp", decodedjwt.getClaim("timestamp").asLong().toString());
        return map;
    }
}
