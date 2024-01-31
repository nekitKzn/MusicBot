package com.nekit.MusicBot.handler.teacher.editProfile;

import com.nekit.MusicBot.handler.Handler;
import com.nekit.MusicBot.service.TeacherService;
import com.nekit.MusicBot.service.UserService;
import com.nekit.MusicBot.state.StateBot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

import static com.nekit.MusicBot.state.StateBot.TEACHER_EDIT;

@Component
@RequiredArgsConstructor
public class TeacherEditContactSuccessfullyHandler implements Handler {

    private final TeacherService teacherService;
    private final UserService userService;

    @Override
    public StateBot getCurrentState() {
        return StateBot.TEACHER_EDIT_CONTACTS_SUCCESSFULLY;
    }

    @Override
    public BotApiMethod<?> handle(Message message) {
        var keyboard = getKeyboardDefault(TEACHER_EDIT);
        teacherService.updateContacts(message);
        userService.updateUserState(message.getChatId(), StateBot.TEACHER_EDIT);
        return getDefaultMessage(message, keyboard);
    }
}
