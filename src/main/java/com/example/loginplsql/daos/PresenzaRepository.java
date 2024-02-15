package com.example.loginplsql.daos;

import com.example.loginplsql.models.Presenza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;

public interface PresenzaRepository extends JpaRepository<Presenza, Integer> {
    @Query(value = "select * from presenze where trunc(data) = trunc(:data) and username = :id", nativeQuery = true)
    Presenza findByDateAndUser(Timestamp data, Number id);

    @Query(value = "select * from attendance where to_char(systimestamp, 'mm/yyyy') like to_char(date_attendance, 'mm/yyyy') order by date_attendance", nativeQuery = true)
    List<Presenza> getSysTimestampMounth();
}
