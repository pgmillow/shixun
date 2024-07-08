package com.penguin;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Jwttest {

    @Test
    public void testGen(){

        Map<String,Object> claims  = new HashMap<>();
        claims.put("id",1);
        claims.put("username","张三");

        String token = JWT.create()
                .withClaim("user",claims)
                .withExpiresAt(new Date(System.currentTimeMillis()+1000*60*60*12))
                .sign(Algorithm.HMAC256("penguin"));

        System.out.println(token);
    }



}
