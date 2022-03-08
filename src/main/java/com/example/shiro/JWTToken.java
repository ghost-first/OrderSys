package com.example.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * Created with IntelliJ IDEA
 *
 * @Author yuanhaoyue swithaoy@gmail.com
 * @Description token
 * @Date 2018-04-09
 * @Time 16:54
 */
public class JWTToken implements AuthenticationToken {
    private String token;

    public JWTToken(String token) {
        System.out.println("在JWTTOKEN初始化token");
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        System.out.println("获得Token");
        return token;
    }

    @Override
    public Object getCredentials() {
        System.out.println("获得Token");
        return token;
    }
}
