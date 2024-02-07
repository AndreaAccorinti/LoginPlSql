package com.example.loginplsql.Utils;
import com.example.loginplsql.models.Presenza;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public final class Utils {
    private static final String PATTERN_FORMAT_DATE = "dd/MM/yyyy";
    private static final String PATTERN_FORMAT_TIME = "H:mm";
    private static final String DESC = "/d";
    private static final String ENTRY_M = "/entry_m";
    private static final String EXIT_M = "/exit_m";
    private static final String ENTRY_P = "/entry_p";
    private static final String EXIT_P = "/exit_p";
    public static Instant getNowDate() {
        Date date = new Date();
        return new Timestamp(date.getTime()).toInstant();
    }
    public static String instantToStringDate(Instant date) {
        if (date == null)
            return "none";
        return DateTimeFormatter.ofPattern(PATTERN_FORMAT_DATE).withZone(ZoneId.systemDefault()).format(date);
    }

    public static String instantToStringTime(Instant date) {
        if (date == null)
            return "none";
        return DateTimeFormatter.ofPattern(PATTERN_FORMAT_TIME).withZone(ZoneId.systemDefault()).format(date);
    }

}
