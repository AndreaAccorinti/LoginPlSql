package com.example.loginplsql.daos;

import com.example.loginplsql.models.Presenza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;

public interface PresenzaRepository extends JpaRepository<Presenza, Integer> {
    String FIND_BY_DATE_AND_USER =
            "select * from attendance where trunc(data) = trunc(:data) and username = :id";

    String GET_SYS_TIMESTAMP_MONTH =
            "select * from attendance where monthname(curdate()) like monthname(date_attendance) order by date_attendance";

    String GET_TOTAL_MONTHLY_WORKING_DAYS =
            "WITH RECURSIVE Date_Ranges AS (" +
                    "SELECT DATE_FORMAT(:data, '%Y-%m-01') as date_ " +
                    "UNION ALL " +
                    "SELECT DATE_ADD(date_, INTERVAL 1 DAY) " +
                    "FROM Date_Ranges " +
                    "WHERE MONTH(date_) = MONTH(DATE_FORMAT(:data, '%Y-%m-01')))" +
                    "SELECT COUNT(*) FROM Date_Ranges " +
                    "WHERE YEAR(date_) = YEAR(:data) AND MONTH(date_) = MONTH(:data) AND DAYOFWEEK(date_) NOT IN (1, 7)";

    String GET_TOT_DAY_ATTENDANCE_FROM_USER_DESCRIPTION =
            "SELECT SUM(TIMESTAMPDIFF(HOUR, morning_i, morning_e) + TIMESTAMPDIFF(HOUR, afternoon_i, afternoon_e))/8 " +
                    "FROM attendance WHERE " +
                    "id_user = :idUser " +
                    "AND description_ like :descrizione " +
                    "AND MONTH(date_attendance) = :mese " +
                    "AND WEEKDAY(date_attendance) NOT IN (5,6)";
    String GET_TOT_DAY_ATTENDANCE_FROM_USER =
            "SELECT SUM(TIMESTAMPDIFF(HOUR, morning_i, morning_e) + TIMESTAMPDIFF(HOUR, afternoon_i, afternoon_e))/8 " +
                    "FROM attendance WHERE " +
                    "id_user = :idUser " +
                    "AND MONTH(date_attendance) = :mese " +
                    "AND WEEKDAY(date_attendance) NOT IN (5,6)";

    @Query(value = FIND_BY_DATE_AND_USER, nativeQuery = true)
    Presenza findByDateAndUser(Timestamp data, Number id);

    @Query(value = GET_SYS_TIMESTAMP_MONTH, nativeQuery = true)
    List<Presenza> getSysTimestampMonth();

    @Query(value = GET_TOTAL_MONTHLY_WORKING_DAYS, nativeQuery = true)
    int getTotalMonthlyWorkingDays(String data);

    @Query(value = GET_TOT_DAY_ATTENDANCE_FROM_USER_DESCRIPTION, nativeQuery = true)
    int getTotDayAttendanceFromUserDescription(int idUser, String descrizione, int mese);

    @Query(value = GET_TOT_DAY_ATTENDANCE_FROM_USER, nativeQuery = true)
    int getTotDayAttendanceFromUser(int idUser, int mese);
}
