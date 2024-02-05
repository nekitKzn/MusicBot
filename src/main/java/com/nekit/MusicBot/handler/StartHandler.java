package com.nekit.MusicBot.handler;

import com.nekit.MusicBot.enumBot.StateBot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;
import java.util.Objects;

import static com.nekit.MusicBot.enumBot.StateBot.*;
import static com.nekit.MusicBot.util.TelegramUtil.createButtonByState;


@Component
@RequiredArgsConstructor
public class StartHandler implements Handler {

    @Override
    public StateBot getCurrentState() {
        return StateBot.START;
    }

    @Override
    public BotApiMethod<?> handle(Message message) {

        var replyKeyboard = InlineKeyboardMarkup.builder()
                .keyboard(List.of(
                        List.of(createButtonByState(CREATE_QUESTION)),
                        List.of(createButtonByState(STORAGE)),
                        List.of(
                                createButtonByState(ABOUT_BOT),
                                createButtonByState(USER_TEACHER_LIST)
                        )
                ))
                .build();


        if (Objects.isNull(message.getPhoto())) {
            return getDefaultMessage(message, replyKeyboard);
        } else {
            return SendMessage.builder()
                    .chatId(message.getChatId())
                    .text(getCurrentState().getMessage())
                    .replyMarkup(replyKeyboard)
                    .build();
        }
    }
}
