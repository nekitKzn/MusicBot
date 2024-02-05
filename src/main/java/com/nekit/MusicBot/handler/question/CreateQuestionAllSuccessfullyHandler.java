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
public class CreateQuestionAllSuccessfullyHandler implements Handler {

    private final QuestionService questionService;
    private final UserService userService;

    @Override
    public StateBot getCurrentState() {
        return StateBot.CREATE_QUESTION_ALL_SUCCESSFULLY;
    }

    @Override
    public BotApiMethod<?> handle(Message message) {
        var user = userService.findByTelegramId(message.getFrom().getId());
        questionService.saveNewQuestionAll(user, message);

        var keyboard = getKeyboardDefault(START);
        userService.updateUserState(message.getChatId(), START);
        return getDefaultMessage(message, keyboard, user.getTelegramFirstName());
    }
}
