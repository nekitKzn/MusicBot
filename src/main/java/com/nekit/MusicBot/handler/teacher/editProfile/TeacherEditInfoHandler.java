package com.nekit.MusicBot.handler.teacher.editProfile;

import com.nekit.MusicBot.enumBot.StateBot;
import com.nekit.MusicBot.handler.Handler;
import com.nekit.MusicBot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
public class TeacherEditInfoHandler implements Handler {

    private final UserService userService;

    @Override
    public StateBot getCurrentState() {
        return StateBot.TEACHER_EDIT_INFO;
    }

    @Override
    public BotApiMethod<?> handle(Message message) {
        userService.updateUserState(message.getChatId(), StateBot.TEACHER_EDIT_INFO_SUCCESSFULLY);
        return SendMessage.builder()
                .chatId(message.getChatId())
                .text(getCurrentState().getMessage())
                .build();
    }
}
