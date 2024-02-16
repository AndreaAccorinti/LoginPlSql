package com.example.loginplsql.models;

public class LoginResponse {
    private String response;
    private String token;
    public LoginResponse(String response) {
        this.response = response;
    }

    public LoginResponse(String response, String token) {
        this.response = response;
        this.token = token;
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
}
