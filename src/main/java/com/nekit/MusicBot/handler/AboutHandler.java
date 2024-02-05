package com.nekit.MusicBot.handler;

import com.nekit.MusicBot.enumBot.StateBot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;

import static com.nekit.MusicBot.enumBot.StateBot.START;
import static com.nekit.MusicBot.util.TelegramUtil.createButtonByState;
import static com.nekit.MusicBot.util.TelegramUtil.createButtonWithUrl;


@Component
@RequiredArgsConstructor
public class AboutHandler implements Handler {

    private static final String URL_CANAL = "https://t.me/+yJubTeRvQ0U0MmEy";

    @Override
    public StateBot getCurrentState() {
        return StateBot.ABOUT_BOT;
    }

    @Override
    public BotApiMethod<?> handle(Message message) {

        var keyboard = InlineKeyboardMarkup.builder()
                .keyboard(List.of(List.of(
                                createButtonWithUrl("Канал", START.name(), URL_CANAL)),
                        List.of(createButtonByState(START))))
                .build();

        return getDefaultMessage(message, keyboard);
    }
}
