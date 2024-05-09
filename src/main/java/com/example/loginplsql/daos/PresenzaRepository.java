package com.example.loginplsql.daos;

import com.example.loginplsql.models.Presenza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;

public interface PresenzaRepository extends JpaRepository<Presenza, Integer> {
    @Query(value = "select * from attendance where trunc(data) = trunc(:data) and username = :id", nativeQuery = true)
    Presenza findByDateAndUser(Timestamp data, Number id);

    @Query(value = "select * from attendance where monthname(curdate()) like monthname(date_attendance) order by date_attendance", nativeQuery = true)
    List<Presenza> getSysTimestampMounth();

    @Query(value =
            "SELECT COUNT(*) " +
            "FROM ( SELECT DAYOFMONTH(date_) AS day FROM ( " +
            "SELECT DATE_ADD(:data, INTERVAL (a.a + (10 * b.a) + (100 * c.a)) DAY) AS date_ " +
            "FROM ( SELECT 0 AS a UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) AS a " +
            "CROSS JOIN " +
            "( SELECT 0 AS a UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) AS b " +
            "CROSS JOIN " +
            "( SELECT 0 AS a UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) AS c " +
            ") AS dates WHERE DATE_FORMAT(date_, '%Y-%m') = :yyyymm " +
            ") AS days WHERE DAYOFWEEK(CONCAT(:yyyymm_, day)) NOT IN (1, 7);",

            nativeQuery = true)
    int getTotalDays(String data, String yyyymm, String yyyymm_);

    @Query(value =
            "SELECT SUM(TIMESTAMPDIFF(HOUR, morning_i, morning_e) + TIMESTAMPDIFF(HOUR, afternoon_i, afternoon_e))/8 "+
            "FROM attendance WHERE " +
            "id_user = :idUser " +
            "AND description_ like :descrizione " +
            "AND MONTH(date_attendance) = :mese " +
            "AND WEEKDAY(date_attendance) NOT IN (5,6);",
            nativeQuery = true)
    int getTotDayAttendanceFromUserDescription(int idUser, String descrizione, int mese);

}
