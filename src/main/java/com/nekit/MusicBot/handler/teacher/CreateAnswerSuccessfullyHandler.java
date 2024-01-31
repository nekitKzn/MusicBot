package com.nekit.MusicBot.handler.teacher;

import com.nekit.MusicBot.handler.Handler;
import com.nekit.MusicBot.service.QuestionService;
import com.nekit.MusicBot.service.TeacherService;
import com.nekit.MusicBot.service.UserService;
import com.nekit.MusicBot.state.StateBot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

import static com.nekit.MusicBot.state.StateBot.TEACHER_MAIN;

@Component
@RequiredArgsConstructor
public class CreateAnswerSuccessfullyHandler implements Handler {

    private final UserService userService;
    private final QuestionService questionService;
    private final TeacherService teacherService;

    @Override
    public StateBot getCurrentState() {
        return StateBot.CREATE_ANSWER_SUCCESSFULLY;
    }

    @Override
    public BotApiMethod<?> handle(Message message) {
        var teacher = teacherService.findByTelegramId(message.getFrom().getId());
        questionService.saveAnswerSelectedQuestion(teacher, message.getText());
        var keyboard = getKeyboardDefault(TEACHER_MAIN);
        userService.updateUserState(message.getChatId(), TEACHER_MAIN);
        return getDefaultMessage(message, keyboard);
    }
}
