package com.example.loginplsql.models;

import jakarta.validation.constraints.Size;

import jakarta.persistence.*;

@Entity
@Table(name = "EXCEL")
@SequenceGenerator(name = "excel_seq_gen", sequenceName = "EXCEL_SEQ", allocationSize = 1)
public class Excel {

    public Excel(){}

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "excel_seq_gen")
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Size(max = 100)
    @Column(name = "URL", length = 100)
    private String url;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
