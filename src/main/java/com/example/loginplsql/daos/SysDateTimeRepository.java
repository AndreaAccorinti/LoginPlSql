package com.example.loginplsql.daos;

import com.example.loginplsql.models.Month;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SysDateTimeRepository extends JpaRepository<Month, Integer> {
    String MONTH_LIST = "SELECT * FROM month";

    @Query(value = MONTH_LIST, nativeQuery = true)
    List<Month> monthList();
}
