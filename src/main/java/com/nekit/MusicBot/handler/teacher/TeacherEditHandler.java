package com.nekit.MusicBot.handler.teacher;

import com.nekit.MusicBot.enumBot.StateBot;
import com.nekit.MusicBot.handler.Handler;
import com.nekit.MusicBot.model.TeacherEntity;
import com.nekit.MusicBot.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;
import java.util.Objects;

import static com.nekit.MusicBot.enumBot.StateBot.*;
import static com.nekit.MusicBot.util.TelegramUtil.createButtonByState;
import static com.nekit.MusicBot.util.TelegramUtil.getStringProfileByTeacherEntity;

@Component
@RequiredArgsConstructor
public class TeacherEditHandler implements Handler {


    private final TeacherService teacherService;

    @Override
    public StateBot getCurrentState() {
        return StateBot.TEACHER_EDIT;
    }

    @Override
    public Object handle(Message message) {
        var keyboard = InlineKeyboardMarkup.builder()
                .keyboard(
                        List.of(
                                List.of(createButtonByState(TEACHER_EDIT_FIO),
                                        createButtonByState(TEACHER_EDIT_CONTACTS)),
                                List.of(createButtonByState(TEACHER_EDIT_INFO),
                                        createButtonByState(TEACHER_EDIT_CHOCOLATE)),
                                List.of(createButtonByState(TEACHER_EDIT_PHOTO)),
                                List.of(createButtonByState(TEACHER_MAIN))
                        ))
                .build();

        TeacherEntity entity = teacherService.findByTelegramId(message.getChatId());

        if (Objects.nonNull(entity.getPhoto())) {
            return SendPhoto.builder()
                    .chatId(message.getChatId())
                    .photo(new InputFile(entity.getPhoto()))
                    .caption(String.format(getCurrentState().getMessage(), getStringProfileByTeacherEntity(entity)))
                    .replyMarkup(keyboard)
                    .build();
        } else {
            return getDefaultMessage(message, keyboard, getStringProfileByTeacherEntity(entity));
        }
    }
}
