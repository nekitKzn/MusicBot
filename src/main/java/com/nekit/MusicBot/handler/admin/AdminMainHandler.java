package com.nekit.MusicBot.handler.admin;

import com.nekit.MusicBot.handler.Handler;
import com.nekit.MusicBot.state.StateBot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;

import static com.nekit.MusicBot.state.StateBot.*;
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
                                List.of(createButtonByState(LIST_USERS),
                                        createButtonByState(ADMIN_TEACHER_LIST)),
                                List.of(createButtonByState(CREATE_NEW_ADMIN)),
                                List.of(createButtonByState(CREATE_NEW_TEACHER)),
                                List.of(createButtonByState(START))
                        ))
                .build();

        return getDefaultMessage(message, keyboard);
    }
}
