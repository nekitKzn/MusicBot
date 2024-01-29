package com.nekit.MusicBot.handler.teacher;

import com.nekit.MusicBot.handler.Handler;
import com.nekit.MusicBot.service.QuestionService;
import com.nekit.MusicBot.service.UserService;
import com.nekit.MusicBot.state.StateBot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
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
        questionService.selectQuestion(question);
        userService.updateUserState(message.getChatId(), StateBot.CREATE_ANSWER_SUCCESSFULLY);
        return getDefaultMessage(message, null, question.getText());
    }
}
