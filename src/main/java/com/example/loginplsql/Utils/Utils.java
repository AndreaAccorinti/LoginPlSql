package com.example.loginplsql.Utils;
import java.util.Date;

public final class Utils {
    public static Number getNowDate() {
        Date date = new Date();
        return date.getTime();
    }
}
