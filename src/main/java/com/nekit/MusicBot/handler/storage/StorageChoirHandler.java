package com.nekit.MusicBot.handler.storage;

import com.nekit.MusicBot.enumBot.FileLesson;
import com.nekit.MusicBot.enumBot.StateBot;
import com.nekit.MusicBot.handler.Handler;
import com.nekit.MusicBot.model.FileEntity;
import com.nekit.MusicBot.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.nekit.MusicBot.enumBot.StateBot.START;
import static com.nekit.MusicBot.enumBot.StateBot.STORAGE_FILE;
import static com.nekit.MusicBot.handler.storage.StorageHandler.FOUND_FILES;
import static com.nekit.MusicBot.handler.storage.StorageHandler.NOT_FOUND_FILES;
import static com.nekit.MusicBot.util.TelegramUtil.button;
import static com.nekit.MusicBot.util.TelegramUtil.createButtonByState;

@Component
@RequiredArgsConstructor
public class StorageChoirHandler implements Handler {

    private final FileService fileService;

    @Override
    public StateBot getCurrentState() {
        return StateBot.STORAGE_CHOIR;
    }

    @Override
    public StateBot getNextState() {
        return STORAGE_FILE;
    }

    @Override
    public Object handle(Message message) {
        Map<Long, FileEntity> map = fileService.getFileMap(FileLesson.CHOIR_FILE);

        List<List<InlineKeyboardButton>> lists = new ArrayList<>(map.entrySet().stream()
                .map(entry -> button(entry.getValue().getFileName(), entry.getKey()))
                .map(List::of).toList());
        lists.add(List.of(createButtonByState(START)));
        var replyKeyboard = InlineKeyboardMarkup.builder()
                .keyboard(lists).build();
        if (map.isEmpty()) {
            return getDefaultMessage(message, replyKeyboard, NOT_FOUND_FILES);
        } else {
            return getDefaultMessage(message, replyKeyboard, FOUND_FILES);
        }
    }
}
