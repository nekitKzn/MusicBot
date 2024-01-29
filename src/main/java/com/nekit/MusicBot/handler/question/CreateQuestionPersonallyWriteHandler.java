package com.nekit.MusicBot.handler.question;

import com.nekit.MusicBot.handler.Handler;
import com.nekit.MusicBot.service.QuestionService;
import com.nekit.MusicBot.service.TeacherService;
import com.nekit.MusicBot.service.UserService;
import com.nekit.MusicBot.state.StateBot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
public class CreateQuestionPersonallyWriteHandler implements Handler {

    private final UserService userService;
    private final TeacherService teacherService;
    private final QuestionService questionService;

    @Override
    public StateBot getCurrentState() {
        return StateBot.CREATE_QUESTION_PERSONALLY_WRITE;
    }

    @Override
    public BotApiMethod<?> handle(Message message) {
        var teacher = teacherService.findById(Long.valueOf(message.getText()));
        var user = userService.findByTelegramId(message.getChat().getId());
        questionService.saveNewQuestionPersonallyWithoutText(user, teacher);
        userService.updateUserState(message.getChatId(), StateBot.CREATE_QUESTION_PERSONALLY_SUCCESSFULLY);
        return getDefaultMessage(message, null);
    }
}
