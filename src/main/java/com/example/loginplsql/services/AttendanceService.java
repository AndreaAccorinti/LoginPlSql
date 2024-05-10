package com.example.loginplsql.services;

import com.example.loginplsql.daos.PresenzaRepository;
import org.jvnet.hk2.annotations.Service;


@Service
public class AttendanceService {
    private final PresenzaRepository presenzaRepository;

    public AttendanceService(PresenzaRepository presenzaRepository) {
        this.presenzaRepository = presenzaRepository;
    }

    /**
     * Compares total days worked by a user against the total working days in the month.
     *
     * @param userId    The ID of the user.
     * @param yearMonth The year and month in "YYYY-MM" format.
     * @return true if the days worked by the user equals the total working days of the month.
     */
    public boolean status(int userId, String yearMonth) {
        int month = Integer.parseInt(yearMonth.substring(5, 7));
        int daysWorkedByUser = presenzaRepository.getTotDayAttendanceFromUser(userId, month);
        int totalWorkingDays = presenzaRepository.getTotalMonthlyWorkingDays(yearMonth);

        return daysWorkedByUser == totalWorkingDays;
    }
}
