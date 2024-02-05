package com.nekit.MusicBot.handler.teacher.editProfile;

import com.nekit.MusicBot.enumBot.StateBot;
import com.nekit.MusicBot.handler.Handler;
import com.nekit.MusicBot.service.TeacherService;
import com.nekit.MusicBot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import static com.nekit.MusicBot.enumBot.StateBot.TEACHER_EDIT_PHOTO;

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
    public Object handle(Message message) {
        var keyboard = getKeyboardDefault(StateBot.TEACHER_EDIT);
        if (!message.hasPhoto()) {
            userService.updateUserState(message.getFrom().getId(), TEACHER_EDIT_PHOTO);
            return SendMessage.builder()
                    .chatId(message.getChatId())
                    .text(TEACHER_EDIT_PHOTO.getMessage())
                    .replyMarkup(keyboard)
                    .build();
        }
        teacherService.updatePhoto(message);
        userService.updateUserState(message.getChatId(), StateBot.TEACHER_EDIT);
        return SendMessage.builder()
                .chatId(message.getChatId())
                .text(getCurrentState().getMessage())
                .replyMarkup(keyboard)
                .build();
    }
}
