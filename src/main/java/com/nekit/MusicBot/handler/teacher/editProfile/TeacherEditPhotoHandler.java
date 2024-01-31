package com.nekit.MusicBot.handler.teacher.editProfile;

import com.nekit.MusicBot.handler.Handler;
import com.nekit.MusicBot.state.StateBot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
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
    public BotApiMethod<?> handle(Message message) {
        var keyboard = getKeyboardDefault(StateBot.TEACHER_MAIN);
        return SendMessage.builder()
                .chatId(message.getChatId())
                .text(getCurrentState().getMessage())
                .replyMarkup(keyboard)
                .build();
    }
}
