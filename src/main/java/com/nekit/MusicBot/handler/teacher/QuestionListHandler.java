package com.nekit.MusicBot.handler.teacher;

import com.nekit.MusicBot.handler.Handler;
import com.nekit.MusicBot.model.TeacherEntity;
import com.nekit.MusicBot.service.QuestionService;
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

import static com.nekit.MusicBot.state.StateBot.CREATE_ANSWER;
import static com.nekit.MusicBot.state.StateBot.TEACHER_MAIN;
import static com.nekit.MusicBot.util.TelegramUtil.button;
import static com.nekit.MusicBot.util.TelegramUtil.createButtonByState;

@Component
@RequiredArgsConstructor
public class QuestionListHandler implements Handler {

    private final static String NOT_FOUND_QUESTION = "Вопросов к сожалению нет \uD83D\uDE22";
    private final static String SUCCESS_FIND_QUESTION = "Чтобы ответить на вопрос, нажмите на него и напишите ответ, он сразу уйдет отправителю!";

    private final QuestionService questionService;
    private final TeacherService teacherService;

    @Override
    public StateBot getCurrentState() {
        return StateBot.QUESTION_LIST;
    }

    @Override
    public StateBot getNextState() {
        return CREATE_ANSWER;
    }

    @Override
    public BotApiMethod<?> handle(Message message) {
        TeacherEntity teacher = teacherService.findByTelegramId(message.getChat().getId());
        Map<Long, String> listQuestion = questionService.getQuestionMap(teacher);

        List<List<InlineKeyboardButton>> lists = new ArrayList<>(listQuestion.entrySet().stream()
                .map(entry -> button(entry.getValue(), entry.getKey()))
                .map(List::of).toList());
        lists.add(List.of(createButtonByState(TEACHER_MAIN)));
        var replyKeyboard = InlineKeyboardMarkup.builder()
                .keyboard(lists).build();

        if (listQuestion.isEmpty()) {
            return getDefaultMessage(message, replyKeyboard, NOT_FOUND_QUESTION);
        } else {
            return getDefaultMessage(message, replyKeyboard, SUCCESS_FIND_QUESTION);
        }
    }
}
