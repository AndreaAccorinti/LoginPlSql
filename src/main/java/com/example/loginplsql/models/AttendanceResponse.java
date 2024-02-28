package com.example.loginplsql.models;

import java.util.List;

public class AttendanceResponse {
    private List<Presenza> attendanceList;
    private String response;

    public List<Presenza> getAttendanceList() {
        return attendanceList;
    }

    public void setAttendanceList(List<Presenza> attendanceList) {
        this.attendanceList = attendanceList;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
