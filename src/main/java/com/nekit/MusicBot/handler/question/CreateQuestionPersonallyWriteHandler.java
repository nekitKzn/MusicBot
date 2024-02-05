package com.nekit.MusicBot.handler.question;

import com.nekit.MusicBot.enumBot.StateBot;
import com.nekit.MusicBot.handler.Handler;
import com.nekit.MusicBot.model.TeacherEntity;
import com.nekit.MusicBot.model.UserEntity;
import com.nekit.MusicBot.service.QuestionService;
import com.nekit.MusicBot.service.TeacherService;
import com.nekit.MusicBot.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
@Slf4j
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
        logSelectedButton(user, teacher);
        questionService.saveNewQuestionPersonallyWithoutText(user, teacher);
        userService.updateUserState(message.getChatId(), StateBot.CREATE_QUESTION_PERSONALLY_SUCCESSFULLY);
        return getDefaultMessage(message, null);
    }

    private static void logSelectedButton(UserEntity user, TeacherEntity teacher) {
        log.info("User @{}:{} select button: {}", user.getTelegramUserName(),
                user.getTelegramFirstName(), teacher.getName());
    }
}
