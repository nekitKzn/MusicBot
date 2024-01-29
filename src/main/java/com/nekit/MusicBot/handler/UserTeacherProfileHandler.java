package com.nekit.MusicBot.handler;

import com.nekit.MusicBot.model.TeacherEntity;
import com.nekit.MusicBot.service.TeacherService;
import com.nekit.MusicBot.state.StateBot;
import com.nekit.MusicBot.util.TelegramUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;
import java.util.Objects;

import static com.nekit.MusicBot.state.StateBot.START;
import static com.nekit.MusicBot.state.StateBot.USER_TEACHER_LIST;
import static com.nekit.MusicBot.util.TelegramUtil.createButtonByState;
import static com.nekit.MusicBot.util.TelegramUtil.getStringProfileByTeacherEntity;

@Component
@RequiredArgsConstructor
public class UserTeacherProfileHandler implements Handler {

    private final TeacherService teacherService;

    @Override
    public StateBot getCurrentState() {
        return StateBot.USER_TEACHER_PROFILE;
    }

    @Override
    public Object handle(Message message) {
        TeacherEntity entity = teacherService.findById(Long.valueOf(message.getText()));
        String profile = TelegramUtil.getStringProfileByTeacherEntity(entity);


        var keyboard = InlineKeyboardMarkup.builder()
                .keyboard(List.of(
                        List.of(createButtonByState(USER_TEACHER_LIST)),
                        List.of(createButtonByState(START))
                )).build();

        if (Objects.nonNull(entity.getPhoto())) {
            return SendPhoto.builder()
                    .chatId(message.getChatId())
                    .photo(new InputFile(entity.getPhoto()))
                    .caption(String.format(getCurrentState().getMessage(), getStringProfileByTeacherEntity(entity)))
                    .replyMarkup(keyboard)
                    .build();
        } else {
            return EditMessageText.builder()
                    .messageId(message.getMessageId())
                    .chatId(message.getChatId())
                    .text(profile)
                    .replyMarkup(keyboard)
                    .build();
        }
    }
}
