package com.nekit.MusicBot.config;

import com.nekit.MusicBot.bot.TelegramBot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import static com.nekit.MusicBot.log.Constant.ERROR_WITH_START_TELEGRAM_BOT_MESSAGE;


@RequiredArgsConstructor
@Component
@Slf4j
public class BotInitializer {

    private final TelegramBot telegramBot;

    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            telegramBotsApi.registerBot(telegramBot);
        } catch (TelegramApiException e) {
            log.error(ERROR_WITH_START_TELEGRAM_BOT_MESSAGE, e);
        }
    }
}
