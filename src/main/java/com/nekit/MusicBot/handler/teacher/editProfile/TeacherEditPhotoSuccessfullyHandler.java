package com.nekit.MusicBot.handler.teacher.editProfile;

import com.nekit.MusicBot.handler.Handler;
import com.nekit.MusicBot.service.TeacherService;
import com.nekit.MusicBot.service.UserService;
import com.nekit.MusicBot.state.StateBot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
public class TeacherEditPhotoSuccessfullyHandler implements Handler {

    private final TeacherService teacherService;
    private final UserService userService;

    @Override
    public StateBot getCurrentState() {
        return StateBot.TEACHER_EDIT_PHOTO_SUCCESSFULLY;
    }

    @Override
    public BotApiMethod<?> handle(Message message) {
        teacherService.updatePhoto(message);
        var keyboard = getKeyboardWithOneButton(StateBot.TEACHER_EDIT);
        userService.updateUserState(message.getChatId(), StateBot.TEACHER_EDIT);
        return SendMessage.builder()
                .chatId(message.getChatId())
                .text(getCurrentState().getMessage())
                .replyMarkup(keyboard)
                .build();
    }
}
