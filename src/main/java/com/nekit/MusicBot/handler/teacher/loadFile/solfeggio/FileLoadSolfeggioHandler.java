package com.nekit.MusicBot.handler.teacher.loadFile.solfeggio;

import com.nekit.MusicBot.enumBot.StateBot;
import com.nekit.MusicBot.handler.Handler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import static com.nekit.MusicBot.enumBot.StateBot.TEACHER_MAIN;

@Component
@RequiredArgsConstructor
public class FileLoadSolfeggioHandler implements Handler {
    @Override
    public StateBot getCurrentState() {
        return StateBot.FILE_LOAD_SOLFEGGIO;
    }

    @Override
    public StateBot getNextState() {
        return StateBot.FILE_LOAD_SOLFEGGIO_NAME;
    }


    @Override
    public Object handle(Message message) {
        var keyboard = getKeyboardDefault(TEACHER_MAIN);
        return getDefaultMessage(message, keyboard);
    }
}
