package com.example.loginplsql.services;

import com.example.loginplsql.Utils.Utils;
import com.example.loginplsql.daos.PresenzaRepository;
import com.example.loginplsql.daos.UserRepository;
import com.example.loginplsql.models.Presenza;
import com.example.loginplsql.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.StringTokenizer;

@Service
public class TelegramBot extends TelegramLongPollingBot {
    @Autowired
    UserRepository daoUser;

    @Autowired
    PresenzaRepository daoPresenza;

    private final String DESC = "/d";
    private final String ENTRY_M = "/entry_m";
    private final String EXIT_M = "/exit_m";
    private final String ENTRY_P = "/entry_p";
    private final String EXIT_P = "/exit_p";
    private Logger log = LoggerFactory.getLogger(TelegramBot.class);
    private String token = "6467099780:AAGeFFvVrOqN3Pxj0G5RPqwuDAEnCCJ37VY";
    private String username = "rollcallf2bot";

    @Override
    public String getBotUsername() {
        return username;
    }
    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update){
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();
            // Process the message and generate a response
            String response = processMessage(messageText);
            String username = update.getMessage().getFrom().getUserName();
            Presenza presenza = new Presenza();
            if (response != null && response.length() > 0 && username != null && username.length() > 0) {
                User user = null;
                try {
                    user = this.daoUser.findByUsername(username);
                } catch (Exception e) {
                    log.info(" -- Error find user by Username --");
                    e.printStackTrace();
                }
                if (user != null) {
                    try {
                        Presenza responcePre = this.daoPresenza.findByDateAndUser(Utils.getNowDate(), user.getId());
                        if (responcePre != null) {
                            presenza = responcePre;
                        }
                    }catch (Exception e) {
                        log.info(" -- Error find presenza by Date --");
                        e.printStackTrace();
                    }
                    if (presenza.getUsername() == null) {
                        presenza.setUsername(user);
                    }
                    if (elaborateMessage(response,DESC)) {
                        String descrizione = new StringTokenizer(response,DESC).nextToken();
                        if (descrizione != null && descrizione.length() > 0) {
                            presenza.setDescrizione(descrizione);
                        }
                    }
                    if (presenza.getData() == null) {
                        presenza.setData(Utils.getNowDate());
                    }
                    presenza.setInizioMattina(elaborateMessage(response,this.ENTRY_M) ?
                            Utils.getNowDate() : presenza.getInizioMattina());
                    presenza.setFineMattina(elaborateMessage(response,this.EXIT_M) ?
                            Utils.getNowDate() : presenza.getFineMattina());
                    presenza.setInizioPomeriggio(elaborateMessage(response,this.ENTRY_P) ?
                            Utils.getNowDate() : presenza.getInizioPomeriggio());
                    presenza.setFinePomeriggio(elaborateMessage(response,this.EXIT_P) ?
                            Utils.getNowDate() : presenza.getFinePomeriggio());
                    try{
                        this.daoPresenza.save(presenza);
                    } catch (Exception e) {
                        log.info(" -- Insert new presenza --");
                        e.printStackTrace();
                    }
                }
            }
            // Send the response back to the user
            try {
                execute(new SendMessage(chatId.toString(), createBotResponce(presenza)));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private String processMessage(String messageText) {
        log.info(messageText);
        return messageText;
    }

    private boolean elaborateMessage(String messageText, String code) {
        return messageText.contains(code);
    }

    private String createBotResponce(Presenza presenza) {
        StringBuilder sb = new StringBuilder();
        sb.append("user: "+presenza.getUsername().getUsername()+" ");
        sb.append("data: "+ Utils.instantToStringDate(presenza.getData())+" ");
        sb.append("entry_m: "+ Utils.instantToStringTime(presenza.getInizioMattina())+" ");
        sb.append("exit_m: "+ Utils.instantToStringTime(presenza.getFineMattina())+" ");
        sb.append("entry_p: "+ Utils.instantToStringTime(presenza.getInizioPomeriggio())+" ");
        sb.append("exit_p: "+ Utils.instantToStringTime(presenza.getFinePomeriggio())+" ");
        sb.append("desc: " + presenza.getDescrizione());
        return sb.toString();
    }

}
