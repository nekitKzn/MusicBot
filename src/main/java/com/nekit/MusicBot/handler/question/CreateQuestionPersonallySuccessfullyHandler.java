package com.nekit.MusicBot.handler.question;

import com.nekit.MusicBot.enumBot.StateBot;
import com.nekit.MusicBot.handler.Handler;
import com.nekit.MusicBot.service.QuestionService;
import com.nekit.MusicBot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

import static com.nekit.MusicBot.enumBot.StateBot.START;

@Component
@RequiredArgsConstructor
public class CreateQuestionPersonallySuccessfullyHandler implements Handler {

    private final UserService userService;
    private final QuestionService questionService;

    @Override
    public StateBot getCurrentState() {
        return StateBot.CREATE_QUESTION_PERSONALLY_SUCCESSFULLY;
    }

    @Override
    public BotApiMethod<?> handle(Message message) {
        var user = userService.findByTelegramId(message.getFrom().getId());
        questionService.addTextInLastQuestion(user, message.getText());
        var keyboard = getKeyboardDefault(START);
        userService.updateUserState(message.getChatId(), START);
        return getDefaultMessage(message, keyboard);
    }
}
