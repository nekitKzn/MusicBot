package com.nekit.MusicBot.handler.teacher.editProfile;

import com.nekit.MusicBot.enumBot.StateBot;
import com.nekit.MusicBot.handler.Handler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
public class TeacherEditPhotoHandler implements Handler {

    @Override
    public StateBot getCurrentState() {
        return StateBot.TEACHER_EDIT_PHOTO;
    }

    @Override
    public StateBot getNextState() {
        return StateBot.TEACHER_EDIT_PHOTO_SUCCESSFULLY;
    }

    @Override
    public Object handle(Message message) {
        var keyboard = getKeyboardDefault(StateBot.TEACHER_MAIN);
        return getSimpleMessage(message, keyboard);
    }
}
