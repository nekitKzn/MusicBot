package com.nekit.MusicBot.handler.admin.addAdmin;

import com.nekit.MusicBot.enumBot.StateBot;
import com.nekit.MusicBot.handler.Handler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import static com.nekit.MusicBot.enumBot.StateBot.ADMIN_MAIN;

@Component
@RequiredArgsConstructor
public class CreateAdminSuccessfullyHandler implements Handler {

    @Override
    public StateBot getCurrentState() {
        return StateBot.CREATE_ADMIN_SUCCESSFULLY;
    }

    @Override
    public BotApiMethod<?> handle(Message message) {
        var keyboard = getKeyboardDefault(ADMIN_MAIN);

        return SendMessage.builder()
                .chatId(message.getChatId())
                .text(getCurrentState().getMessage())
                .replyMarkup(keyboard)
                .build();
    }
}
