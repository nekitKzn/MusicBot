package com.nekit.MusicBot.bot;

import com.nekit.MusicBot.service.UpdateHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Objects;

import static com.nekit.MusicBot.log.Constant.ERROR_WITH_SENDING_MESSAGE;


@Component
@RequiredArgsConstructor
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

    private final UpdateHandler updateHandler;
    @Value("${bot.token}")
    private String token;
    @Value("${bot.name}")
    private String botName;

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (Objects.nonNull(update.getMyChatMember()) || messageFromGroup(update)) {
            return;
        }

        Object message = updateHandler.handleMessage(update);

        try {
            if (message instanceof SendMessage sendMessage) {
                execute(sendMessage);
            } else if (message instanceof SendPhoto) {
                execute((SendPhoto) message);
            } else {
                execute((EditMessageText) message);
            }

        } catch (TelegramApiException e) {
            log.info(ERROR_WITH_SENDING_MESSAGE, e);
        }
    }

    private boolean messageFromGroup(Update update) {
        boolean result = true;
        if (Objects.nonNull(update.getMessage()) && update.getMessage().isUserMessage()) {
            result = false;
        }
        if (Objects.nonNull(update.getCallbackQuery()) && update.getCallbackQuery().getMessage().isUserMessage()) {
            result = false;
        }
        return result;
    }
}
