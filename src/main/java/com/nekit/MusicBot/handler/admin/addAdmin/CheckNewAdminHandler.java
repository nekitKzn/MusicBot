package com.nekit.MusicBot.handler.admin.addAdmin;

import com.nekit.MusicBot.handler.Handler;
import com.nekit.MusicBot.model.UserEntity;
import com.nekit.MusicBot.service.LetterSender;
import com.nekit.MusicBot.service.UserService;
import com.nekit.MusicBot.state.StateBot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Objects;

import static com.nekit.MusicBot.state.StateBot.ADMIN_MAIN;

@Component
@RequiredArgsConstructor
public class CheckNewAdminHandler implements Handler {

    private final UserService userService;
    private final LetterSender letterSender;

    private final static String SUCCESS_MESSAGE = "Пользователь с таким именем найден успешно. \uD83D\uDD25 " +
            "Добавляем нового администратора в систему! Теперь у него будет доступна админка с полным управлением бота";
    private final static String FAILED_NOT_FOUND_MESSAGE = "Пользователь не найден! \uD83D\uDE14 Попробйте ввести правильный username человека. " +
            "Учтите что, добавляемый вами администратор должен активировать данного бота (зайти и просто нажать start)";

    private final static String FAILED_EXIST_MESSAGE = "Этот пользователь уже добавлен в систему как администратор!";


    @Override
    public StateBot getCurrentState() {
        return StateBot.CHECK_NEW_ADMIN;
    }

    @Override
    public BotApiMethod<?> handle(Message message) {
        UserEntity user = userService.findByUserName(message.getText());
        String text;

        var keyboard = getKeyboardDefault(ADMIN_MAIN);

        if (Objects.isNull(user)) {
            text = FAILED_NOT_FOUND_MESSAGE;
        } else if (user.getAdmin()) {
            text = FAILED_EXIST_MESSAGE;
        } else {
            userService.saveAdmin(user);
            userService.updateUserState(message.getChatId(), StateBot.CREATE_ADMIN_SUCCESSFULLY);
            letterSender.letterNewAdmin(user);
            text = SUCCESS_MESSAGE;
        }

        return SendMessage.builder()
                .chatId(message.getChatId())
                .text(text)
                .replyMarkup(keyboard)
                .build();
    }
}
