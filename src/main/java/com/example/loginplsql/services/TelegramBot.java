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

@Service
public class TelegramBot extends TelegramLongPollingBot {
    @Autowired
    UserRepository daoUser;

    @Autowired
    PresenzaRepository daoPresenza;
    private final Logger log = LoggerFactory.getLogger(TelegramBot.class);
    private final String token = "6467099780:AAGeFFvVrOqN3Pxj0G5RPqwuDAEnCCJ37VY";
    private final String username = "rollcallf2bot";

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
            String response = Utils.processMessage(messageText);
            String username = update.getMessage().getFrom().getUserName();
            Presenza presenza = new Presenza();
            if (response.equals("/button")) {
                try {
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(chatId.toString());
                    Utils.setButtons(sendMessage);
                    sendMessage.setText("SET UP BUTTONS! :)");
                    execute(sendMessage);
                    presenza = null;
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (response.length() > 0 && username != null && username.length() > 0) {
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
                    Utils.setDescrizioneByResponse(presenza, response);
                    if (presenza.getData() == null) {
                        presenza.setData(Utils.getNowDate());
                    }
                    Utils.setDateByResoponse(presenza, response);
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
                if (presenza != null) {
                    execute(new SendMessage(chatId.toString(), Utils.createBotResponce(presenza)));
                }
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

}
