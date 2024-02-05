package com.nekit.MusicBot.handler.teacher;

import com.nekit.MusicBot.enumBot.StateBot;
import com.nekit.MusicBot.handler.Handler;
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
public class TeacherMainHandler implements Handler {

    @Override
    public StateBot getCurrentState() {
        return StateBot.TEACHER_MAIN;
    }

    @Override
    public BotApiMethod<?> handle(Message message) {
        var keyboard = InlineKeyboardMarkup.builder()
                .keyboard(
                        List.of(
                                List.of(createButtonByState(QUESTION_LIST)),
                                List.of(createButtonByState(FILE_LOAD)),
                                List.of(createButtonByState(TEACHER_EDIT)),
                                List.of(createButtonByState(START)))
                )
                .build();

        if (Objects.nonNull(message.getPhoto())) {
            return SendMessage.builder()
                    .chatId(message.getChatId())
                    .text(getCurrentState().getMessage())
                    .replyMarkup(keyboard)
                    .build();
        } else {
            return getDefaultMessage(message, keyboard);
        }
    }
}
