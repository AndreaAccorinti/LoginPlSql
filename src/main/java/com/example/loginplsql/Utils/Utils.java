package com.example.loginplsql.Utils;
import com.example.loginplsql.models.Presenza;
import com.example.loginplsql.services.TelegramBot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

public final class Utils {
    private static final String PATTERN_FORMAT_DATE = "dd/MM/yyyy";
    private static final String PATTERN_FORMAT_TIME = "H:mm";
    private static final String DESC = "/d";
    private static final String ENTRY_M = "/entry_m";
    private static final String EXIT_M = "/exit_m";
    private static final String ENTRY_P = "/entry_p";
    private static final String EXIT_P = "/exit_p";

    private static Logger log = LoggerFactory.getLogger(TelegramBot.class);
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
        sb.append("data: "+ Utils.instantToStringDate(presenza.getData())+"\n");
        sb.append("entry_m: "+ Utils.instantToStringTime(presenza.getInizioMattina())+"\n");
        sb.append("exit_m: "+ Utils.instantToStringTime(presenza.getFineMattina())+"\n");
        sb.append("entry_p: "+ Utils.instantToStringTime(presenza.getInizioPomeriggio())+"\n");
        sb.append("exit_p: "+ Utils.instantToStringTime(presenza.getFinePomeriggio())+"\n");
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

        List<KeyboardRow> keyboard = new ArrayList<KeyboardRow>();
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

}
