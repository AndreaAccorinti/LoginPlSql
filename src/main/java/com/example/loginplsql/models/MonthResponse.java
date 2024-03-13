package com.example.loginplsql.models;

import java.util.List;

public class MonthResponse {
    private List<Month> monthList;
    private String response;

    public List<Month> getMonthList() {
        return monthList;
    }

    public void setMonthList(List<Month> monthList) {
        this.monthList = monthList;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
