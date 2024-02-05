package com.nekit.MusicBot.handler.admin;

import com.nekit.MusicBot.enumBot.StateBot;
import com.nekit.MusicBot.handler.Handler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;

import static com.nekit.MusicBot.enumBot.StateBot.*;
import static com.nekit.MusicBot.util.TelegramUtil.createButtonByState;


@Component
@RequiredArgsConstructor
public class AdminMainHandler implements Handler {

    @Override
    public StateBot getCurrentState() {
        return StateBot.ADMIN_MAIN;
    }

    @Override
    public BotApiMethod<?> handle(Message message) {
        var keyboard = InlineKeyboardMarkup.builder()
                .keyboard(
                        List.of(
                                List.of(createButtonByState(ADMIN_LIST_USERS_ALL_COUNT),
                                        createButtonByState(ADMIN_LIST_USERS_UPDATE_COUNT)),
                                List.of(createButtonByState(CREATE_NEW_ADMIN)),
                                List.of(createButtonByState(CREATE_NEW_TEACHER)),
                                List.of(createButtonByState(START))
                        ))
                .build();

        return getDefaultMessage(message, keyboard);
    }
}
