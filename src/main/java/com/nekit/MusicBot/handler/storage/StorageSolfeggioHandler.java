package com.nekit.MusicBot.handler.storage;

import com.nekit.MusicBot.handler.Handler;
import com.nekit.MusicBot.state.StateBot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import static com.nekit.MusicBot.handler.storage.StorageHandler.NOT_FOUND_FILES;

@Component
@RequiredArgsConstructor
public class StorageSolfeggioHandler implements Handler {
    @Override
    public StateBot getCurrentState() {
        return StateBot.STORAGE_SOLFEGGIO;
    }

    @Override
    public Object handle(Message message) {
        var keyboard = getKeyboardWithOneButton(StateBot.START);
        return getDefaultMessage(message, keyboard, NOT_FOUND_FILES);
    }
}
