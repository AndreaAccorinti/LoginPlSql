package com.example.loginplsql.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "month")
public class Month {
    @Id
    private Integer id;
    private String month_name;
    private String month_format;

    public Month(Integer id, String month_name, String month_format) {
        this.id = id;
        this.month_name = month_name;
        this.month_format = month_format;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMonth_name() {
        return month_name;
    }

    public void setMonth_name(String month_name) {
        this.month_name = month_name;
    }

    public String getMonth_format() {
        return month_format;
    }

    public void setMonth_format(String month_format) {
        this.month_format = month_format;
    }
}
