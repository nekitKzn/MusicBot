package com.nekit.MusicBot.handler.teacher.loadFile.choir;

import com.nekit.MusicBot.enumBot.StateBot;
import com.nekit.MusicBot.handler.Handler;
import com.nekit.MusicBot.service.FileService;
import com.nekit.MusicBot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
public class LoadFileChoirSuccessfullyHandler implements Handler {

    private final UserService userService;
    private final FileService fileService;

    @Override
    public StateBot getCurrentState() {
        return StateBot.FILE_LOAD_CHOIR_SUCCESSFULLY;
    }

    @Override
    public Object handle(Message message) {
        if (!message.hasReplyMarkup()) { // значит не нажата кнопка "взять название из файла"
            fileService.updateNameFile(message.getFrom().getId(), message.getText());
        }
        var keyboard = getKeyboardDefault(StateBot.TEACHER_MAIN);
        userService.updateUserState(message.getChatId(), StateBot.TEACHER_MAIN);
        return getDefaultMessage(message, keyboard);
    }
}
