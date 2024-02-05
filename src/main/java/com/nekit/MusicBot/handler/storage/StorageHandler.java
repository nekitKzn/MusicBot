package com.nekit.MusicBot.handler.storage;

import com.nekit.MusicBot.enumBot.StateBot;
import com.nekit.MusicBot.handler.Handler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;

import static com.nekit.MusicBot.enumBot.StateBot.*;
import static com.nekit.MusicBot.util.TelegramUtil.createButtonByState;

@Component
@RequiredArgsConstructor
public class StorageHandler implements Handler {

    protected static final String NOT_FOUND_FILES = "К сожалению, тут пока пусто ✨";
    protected static final String FOUND_FILES = "Выберите нужный файл:";

    @Override
    public StateBot getCurrentState() {
        return StateBot.STORAGE;
    }

    @Override
    public Object handle(Message message) {
        var replyKeyboard = InlineKeyboardMarkup.builder()
                .keyboard(List.of(
                        List.of(
                                createButtonByState(STORAGE_ORCHESTRA),
                                createButtonByState(STORAGE_CHOIR)
                        ),
                        List.of(
                                createButtonByState(STORAGE_SOLFEGGIO),
                                createButtonByState(STORAGE_OTHER)
                        ),
                        List.of(createButtonByState(START))
                ))
                .build();
        return getDefaultMessage(message, replyKeyboard);
    }
}
