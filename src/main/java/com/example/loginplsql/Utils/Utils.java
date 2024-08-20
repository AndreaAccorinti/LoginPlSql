package com.example.loginplsql.Utils;
import com.example.loginplsql.models.Presenza;
import com.example.loginplsql.services.TelegramBot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public final class Utils {
    private static final String PATTERN_FORMAT_DATE = "dd/MM/yyyy";
    private static final String PATTERN_FORMAT_TIME = "H:mm";
    private static final String DESC = "/d";
    private static final String ENTRY_M = "/entry_m";
    private static final String EXIT_M = "/exit_m";
    private static final String ENTRY_P = "/entry_p";
    private static final String EXIT_P = "/exit_p";

    public static final String CORSURL = "http://deploy-ang.s3-website.eu-west-3.amazonaws.com";

    private final static Logger log = LoggerFactory.getLogger(TelegramBot.class);
    public static Timestamp getNowDate() {
        Date date = new Date();
        return new Timestamp(date.getTime());
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

    public static String timestampToStringTime(Timestamp date) {
        if (date == null)
            return "none";
        return date.toString();
    }

    public static Timestamp stringToTimestamp(String date) {
        Date dt = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            dt = sdf.parse(date);

        } catch (ParseException parseException) {
            log.info(parseException.getMessage());
        }
        if (dt != null)
            return new Timestamp(dt.getTime());
        return null;
    }

    public static String timestampToStringDDMMYYYY(Timestamp timestamp) {
        String format = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(timestamp);
    }

    public static String timestampToStringYYYY(Timestamp timestamp) {
        String format = "yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(timestamp);
    }

    public static void setDateByResoponse(Presenza presenza, String response) {
        presenza.setInizioMattina(elaborateMessage(response,ENTRY_M) ?
                Utils.getNowDate() : presenza.getInizioMattina());
        presenza.setFineMattina(elaborateMessage(response,EXIT_M) ?
                Utils.getNowDate() : presenza.getFineMattina());
        presenza.setInizioPomeriggio(elaborateMessage(response,ENTRY_P) ?
                Utils.getNowDate() : presenza.getInizioPomeriggio());
        presenza.setFinePomeriggio(elaborateMessage(response,EXIT_P) ?
                Utils.getNowDate() : presenza.getFinePomeriggio());
    }

    public  static void setDescrizioneByResponse(Presenza presenza, String response) {
        if (elaborateMessage(response,DESC)) {
            String descrizione = new StringTokenizer(response,DESC).nextToken();
            if (descrizione != null && descrizione.length() > 0) {
                presenza.setDescrizione(descrizione);
            }
        }
    }

    public static boolean elaborateMessage(String messageText, String code) {
        return messageText.contains(code);
    }

    public static String createBotResponce(Presenza presenza) {
        StringBuilder sb = new StringBuilder();
        sb.append("user: "+presenza.getUsername().getUsername()+"\n");
        sb.append("data: "+ Utils.timestampToStringTime(presenza.getData())+"\n");
        sb.append("entry_m: "+ Utils.timestampToStringTime(presenza.getInizioMattina())+"\n");
        sb.append("exit_m: "+ Utils.timestampToStringTime(presenza.getFineMattina())+"\n");
        sb.append("entry_p: "+ Utils.timestampToStringTime(presenza.getInizioPomeriggio())+"\n");
        sb.append("exit_p: "+ Utils.timestampToStringTime(presenza.getFinePomeriggio())+"\n");
        sb.append("desc: " + presenza.getDescrizione());
        return sb.toString();
    }

    public static String processMessage(String messageText) {
        log.info(messageText);
        return messageText;
    }

    public static void setButtons(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardRowFirstRow = new KeyboardRow();
        keyboardRowFirstRow.add(ENTRY_M);
        keyboardRowFirstRow.add(EXIT_M);
        KeyboardRow keyboardRowSecondRow = new KeyboardRow();
        keyboardRowSecondRow.add(ENTRY_P);
        keyboardRowSecondRow.add(EXIT_P);
        keyboard.add(keyboardRowFirstRow);
        keyboard.add(keyboardRowSecondRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
    }

    public static List<String> getMounthDay(Timestamp date) {
        List<String> listDay = new ArrayList<>();
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(ZoneId.systemDefault()));
        calendar.setTimeInMillis(date.getTime());
        int anno = calendar.get(Calendar.YEAR);
        int mese = calendar.get(Calendar.MONTH) + 1;
        int max_day = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int i = 1; i<=max_day; i++) {
            listDay.add(Utils.formatDate(Integer.toString(i), Integer.toString(mese), Integer.toString(anno)));
        }
        return listDay;
     }

     public static Map<String, Presenza> listToMapAttendance(List<Presenza> listAttendance) {
        Map<String,Presenza> map = new HashMap<>();
        for (Presenza pr : listAttendance) {
            map.put(Utils.timestampToStringDDMMYYYY(pr.getData()), pr);
        }
        return map;
     }

     public static String formatDate(String d, String m, String y) {
        return Utils.addZero(d) + "/" + Utils.addZero(m) + "/" + y;
     }

     public static String addZero(String d) {
        if (d.length() < 2)
            return "0"+d;
        return d;
     }

     public static String timestampToTime(Timestamp timestamp) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(timestamp);
     }

     public static String dateToNameDay(String day) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALIAN);
            Date dt = sdf.parse(day);
            sdf = new SimpleDateFormat("EEEE", Locale.ITALIAN);
            return sdf.format(dt);
        } catch (ParseException parseException) {
            return parseException.getMessage();
        }
     }


}
