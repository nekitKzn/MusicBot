package com.nekit.MusicBot.handler.teacher.loadFile;

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
public class LoadFileHandler implements Handler {

    @Override
    public StateBot getCurrentState() {
        return StateBot.FILE_LOAD;
    }

    @Override
    public Object handle(Message message) {
        var keyboard = InlineKeyboardMarkup.builder()
                .keyboard(
                        List.of(
                                List.of(createButtonByState(FILE_LOAD_CHOIR),
                                        createButtonByState(FILE_LOAD_ORCHESTRA)),
                                List.of(createButtonByState(FILE_LOAD_SOLFEGGIO),
                                        createButtonByState(FILE_LOAD_OTHER)),
                                List.of(createButtonByState(TEACHER_MAIN))
                        ))
                .build();
        return getDefaultMessage(message, keyboard);
    }
}
