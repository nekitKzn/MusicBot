package com.nekit.MusicBot.handler.question;

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
public class CreateQuestionHandler implements Handler {

    @Override
    public StateBot getCurrentState() {
        return StateBot.CREATE_QUESTION;
    }

    @Override
    public BotApiMethod<?> handle(Message message) {
        var replyKeyboard = InlineKeyboardMarkup.builder()
                .keyboard(List.of(
                        List.of(createButtonByState(CREATE_QUESTION_ALL),
                                createButtonByState(CREATE_QUESTION_PERSONALLY)),
                        List.of(
                                createButtonByState(START)
                        )
                ))
                .build();
        return getDefaultMessage(message, replyKeyboard);
    }
}
