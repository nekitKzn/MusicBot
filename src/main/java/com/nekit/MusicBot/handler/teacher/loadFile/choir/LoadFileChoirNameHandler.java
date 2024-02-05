package com.nekit.MusicBot.handler.teacher.loadFile.choir;

import com.nekit.MusicBot.enumBot.FileLesson;
import com.nekit.MusicBot.enumBot.StateBot;
import com.nekit.MusicBot.handler.Handler;
import com.nekit.MusicBot.service.FileService;
import com.nekit.MusicBot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
public class LoadFileChoirNameHandler implements Handler {

    private final FileService fileService;
    private final UserService userService;

    @Override
    public StateBot getCurrentState() {
        return StateBot.FILE_LOAD_CHOIR_NAME;
    }

    @Override
    public Object handle(Message message) {
        var keyboard = getKeyboardDefault(StateBot.FILE_LOAD_CHOIR_SUCCESSFULLY);
        if (message.hasPhoto()) { // если фото, то ввести название нужно обязательно
            keyboard = null;
        }
        fileService.saveNewFile(message, FileLesson.CHOIR_FILE);
        userService.updateUserState(message.getChatId(), StateBot.FILE_LOAD_CHOIR_SUCCESSFULLY);
        return getDefaultMessage(message, keyboard);
    }
}
