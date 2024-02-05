package com.nekit.MusicBot.handler.teacher.editProfile;

import com.nekit.MusicBot.enumBot.StateBot;
import com.nekit.MusicBot.handler.Handler;
import com.nekit.MusicBot.service.TeacherService;
import com.nekit.MusicBot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import static com.nekit.MusicBot.enumBot.StateBot.TEACHER_EDIT;

@Component
@RequiredArgsConstructor
public class TeacherEditFioSuccessfullyHandler implements Handler {

    private final TeacherService teacherService;
    private final UserService userService;

    @Override
    public StateBot getCurrentState() {
        return StateBot.TEACHER_EDIT_FIO_SUCCESSFULLY;
    }

    @Override
    public BotApiMethod<?> handle(Message message) {
        InlineKeyboardMarkup keyboard = getKeyboardDefault(TEACHER_EDIT);
        teacherService.updateFio(message);
        userService.updateUserState(message.getChatId(), StateBot.TEACHER_EDIT);
        return getDefaultMessage(message, keyboard);
    }
}
