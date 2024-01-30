package com.nekit.MusicBot.event;

import com.nekit.MusicBot.bot.TelegramBot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static com.nekit.MusicBot.log.Constant.ERROR_WITH_SENDING_MESSAGE;


@Component
@Slf4j
@RequiredArgsConstructor
public class ServiceEventListener {

    private final TelegramBot telegramBot;

    @Async
    @EventListener
    public void handleSendTelegramMessageEvent(SendTelegramMessageEvent event) {
        try {
            telegramBot.execute(
                    SendMessage.builder()
                            .replyToMessageId(event.getReplyMessageId())
                            .text(event.getText())
                            .chatId(event.getChatId())
                            .build()
            );
        } catch (TelegramApiException e) {
            log.debug(ERROR_WITH_SENDING_MESSAGE, e);
        }
    }
}
