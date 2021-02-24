package com.goufn.permission.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.shiro.authc.AuthenticationToken;
@Data
@AllArgsConstructor
public class JWTToken implements AuthenticationToken {
    private String token;

    private String exipreAt;

    public JWTToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
