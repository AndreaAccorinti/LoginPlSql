package com.example.loginplsql.models;

public class LoginResponse {
    private String response;
    private String token;
    private User user;
    public LoginResponse(String response) {
        this.response = response;
    }

    public LoginResponse(String response, String token) {
        this.response = response;
        this.token = token;
    }

    public LoginResponse(String response, String token, User user) {
        this.response = response;
        this.token = token;
        this.user = user;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
