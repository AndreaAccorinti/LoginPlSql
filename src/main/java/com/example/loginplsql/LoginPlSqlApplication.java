package com.example.loginplsql;

import com.example.loginplsql.services.TelegramBot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
public class LoginPlSqlApplication {


    public static void main(String[] args) {
        SpringApplication.run(LoginPlSqlApplication.class, args);
    }

    @Bean
    public TelegramBotsApi telegramBotsApi() throws TelegramApiException {
        // Initialize the TelegramBotsApi with the DefaultBotSession class
        return new TelegramBotsApi(DefaultBotSession.class);
    }

    @Bean
    public TelegramBot telegramBot(TelegramBotsApi telegramBotsApi) {
        TelegramBot telegramBot = new TelegramBot();
        try {
            // Register the bot here, so Spring manages the bot lifecycle
            telegramBotsApi.registerBot(telegramBot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            // Consider logging this exception or handling it appropriately
        }
        return telegramBot;
    }

}
