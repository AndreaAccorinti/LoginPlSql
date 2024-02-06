package com.example.loginplsql.services;

import com.example.loginplsql.Util.Util;
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
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();
            // Process the message and generate a response
            String response = processMessage(messageText);
            String username = update.getMessage().getFrom().getUserName();
            Presenza presenza = new Presenza();
            if (response != null && response.length() > 0 && username != null && username.length() > 0) {
                User user = this.daoUser.findByUsername(username);
                if (user != null) {
                    presenza.setUsername(user);
                    String [] descrizione = response.split(this.DESC);
                    if (descrizione != null && descrizione.length > 0) {
                        presenza.setDescrizione(descrizione[1]);
                    }
                    presenza.setId(1L);
                    presenza.setData(Util.getNowDate());
                    presenza.setInizioMattina(elaborateMessage(response,this.ENTRY_M) ?
                            Util.getNowDate() : null);
                    presenza.setFineMattina(elaborateMessage(response,this.EXIT_M) ?
                            Util.getNowDate() : null);
                    presenza.setInizioPomeriggio(elaborateMessage(response,this.ENTRY_P) ?
                            Util.getNowDate() : null);
                    presenza.setFinePomeriggio(elaborateMessage(response,this.EXIT_P) ?
                            Util.getNowDate() : null);

                    this.daoPresenza.save(presenza);
                }
            }
            // Send the response back to the user
            try {
                execute(new SendMessage(chatId.toString(), response));
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
        return messageText.split(code) != null && messageText.length() > 0;
    }
}
