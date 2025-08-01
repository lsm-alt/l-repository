package com.codedata.rbac.security.auth;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * 获取认证令牌
 * getPrincipal  获取委托人信息
 * getPrincipal  获取证明信息
 * */
public class OAuth2AuthenticationToken implements AuthenticationToken {
    private String token;

    public OAuth2AuthenticationToken(String token) {
        this.token = token;
    }

    @Override
    public String getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
