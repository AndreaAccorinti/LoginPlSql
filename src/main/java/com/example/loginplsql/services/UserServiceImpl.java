package com.example.loginplsql.services;
import com.example.loginplsql.models.User;
import io.jsonwebtoken.Jwts;
import org.jvnet.hk2.annotations.Service;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Service
public class UserServiceImpl {
    private String token;

    private boolean isLogged;

    public void authenticate(User user) {
        this.setToken(Jwts.builder()
                .claim("name", user.getName())
                .claim("email", user.getEmail())
                .setSubject(user.getUsername())
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusMillis(7200000)))
                .compact());
        this.setLogged(true);
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public boolean isLogged() {
        return isLogged;
    }

    public void setLogged(boolean logged) {
        isLogged = logged;
    }
}

