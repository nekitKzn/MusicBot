package com.nekit.MusicBot.handler.question;

import com.nekit.MusicBot.handler.Handler;
import com.nekit.MusicBot.service.UserService;
import com.nekit.MusicBot.state.StateBot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

import static com.nekit.MusicBot.state.StateBot.START;

@Component
@RequiredArgsConstructor
public class CreateQuestionAllHandler implements Handler {

    private final UserService userService;

    @Override
    public StateBot getCurrentState() {
        return StateBot.CREATE_QUESTION_ALL;
    }

    @Override
    public BotApiMethod<?> handle(Message message) {
        var keyboard = getKeyboardDefault(START);
        userService.updateUserState(message.getChatId(), StateBot.CREATE_QUESTION_ALL_SUCCESSFULLY);
        return getDefaultMessage(message, keyboard);
    }
}
