package com.nekit.MusicBot.handler.teacher;

import com.nekit.MusicBot.handler.Handler;
import com.nekit.MusicBot.model.QuestionEntity;
import com.nekit.MusicBot.service.QuestionService;
import com.nekit.MusicBot.service.UserService;
import com.nekit.MusicBot.state.StateBot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreateAnswerHandler implements Handler {

    private final QuestionService questionService;
    private final UserService userService;

    @Override
    public StateBot getCurrentState() {
        return StateBot.CREATE_ANSWER;
    }

    @Override
    public BotApiMethod<?> handle(Message message) {
        var question = questionService.findById(Long.valueOf(message.getText()));
        logSelectedButton(message, question);
        questionService.selectQuestion(question);
        userService.updateUserState(message.getChatId(), StateBot.CREATE_ANSWER_SUCCESSFULLY);
        return getDefaultMessage(message, null, question.getText());
    }

    private static void logSelectedButton(Message message, QuestionEntity question) {
        log.info("User @{}:{} select button: {}", message.getChat().getUserName(),
                message.getChat().getFirstName(), question.getText());
    }
}
