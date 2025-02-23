package com.nekit.MusicBot.handler;

import com.nekit.MusicBot.enumBot.StateBot;
import com.nekit.MusicBot.model.TeacherEntity;
import com.nekit.MusicBot.service.TeacherService;
import com.nekit.MusicBot.util.TelegramUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;
import java.util.Objects;

import static com.nekit.MusicBot.enumBot.StateBot.START;
import static com.nekit.MusicBot.enumBot.StateBot.USER_TEACHER_LIST;
import static com.nekit.MusicBot.util.TelegramUtil.createButtonByState;
import static com.nekit.MusicBot.util.TelegramUtil.getStringProfileByTeacherEntity;

@Slf4j
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
        if (!NumberUtils.isCreatable(message.getText())) {
            return null;
        }
        TeacherEntity entity = teacherService.findById(Long.valueOf(message.getText()));
        String profile = TelegramUtil.getStringProfileByTeacherEntity(entity);

        logSelectedButton(message, entity);

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

    private static void logSelectedButton(Message message, TeacherEntity teacher) {
        log.info("User @{}:{} select button: {}", message.getChat().getUserName(),
                message.getChat().getFirstName(), teacher.getFio());
    }
}
