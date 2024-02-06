package com.example.loginplsql.Util;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;

public final class Util {
    public static Instant getNowDate() {
        Date date = new Date();
        return new Timestamp(date.getTime()).toInstant();
    }
}
