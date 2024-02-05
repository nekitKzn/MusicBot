package com.nekit.MusicBot.handler;

import com.nekit.MusicBot.enumBot.StateBot;
import com.nekit.MusicBot.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.nekit.MusicBot.enumBot.StateBot.START;
import static com.nekit.MusicBot.util.TelegramUtil.button;
import static com.nekit.MusicBot.util.TelegramUtil.createButtonByState;

@Component
@RequiredArgsConstructor
public class UserTeacherListHandler implements Handler {

    private final TeacherService teacherService;

    @Override
    public StateBot getCurrentState() {
        return StateBot.USER_TEACHER_LIST;
    }

    @Override
    public StateBot getNextState() {
        return StateBot.USER_TEACHER_PROFILE;
    }

    @Override
    public BotApiMethod<?> handle(Message message) {
        Map<Long, String> listName = teacherService.getTeacherMap();

        List<List<InlineKeyboardButton>> lists = new ArrayList<>(listName.entrySet().stream()
                .map(entry -> button(entry.getValue(), entry.getKey()))
                .map(List::of).toList());
        lists.add(List.of(createButtonByState(START)));
        var replyKeyboard = InlineKeyboardMarkup.builder()
                .keyboard(lists).build();

        if (Objects.isNull(message.getPhoto())) {
            return getDefaultMessage(message, replyKeyboard);
        } else {
            return SendMessage.builder()
                    .chatId(message.getChatId())
                    .text(getCurrentState().getMessage())
                    .replyMarkup(replyKeyboard)
                    .build();
        }
    }
}
