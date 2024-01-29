package com.nekit.MusicBot.handler.question;

import com.nekit.MusicBot.handler.Handler;
import com.nekit.MusicBot.service.TeacherService;
import com.nekit.MusicBot.state.StateBot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.nekit.MusicBot.state.StateBot.START;
import static com.nekit.MusicBot.util.TelegramUtil.button;
import static com.nekit.MusicBot.util.TelegramUtil.createButtonByState;

@Component
@RequiredArgsConstructor
public class CreateQuestionPersonallyHandler implements Handler {

    private final TeacherService teacherService;

    @Override
    public StateBot getCurrentState() {
        return StateBot.CREATE_QUESTION_PERSONALLY;
    }

    @Override
    public StateBot getNextState() {
        return StateBot.CREATE_QUESTION_PERSONALLY_WRITE;
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
        return getDefaultMessage(message, replyKeyboard);
    }
}
