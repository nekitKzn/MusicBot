package com.nekit.MusicBot.handler.admin.addTeacher;

import com.nekit.MusicBot.handler.Handler;
import com.nekit.MusicBot.service.UserService;
import com.nekit.MusicBot.state.StateBot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

import static com.nekit.MusicBot.state.StateBot.ADMIN_MAIN;

@Component
@RequiredArgsConstructor
public class CreateNewTeacherHandler implements Handler {

    private final UserService userService;

    @Override
    public StateBot getCurrentState() {
        return StateBot.CREATE_NEW_TEACHER;
    }

    @Override
    public BotApiMethod<?> handle(Message message) {

        var keyboard = getKeyboardWithOneButton(ADMIN_MAIN);

        userService.updateUserState(message.getChatId(), StateBot.CHECK_NEW_TEACHER);

        return getDefaultMessage(message, keyboard);
    }
}
