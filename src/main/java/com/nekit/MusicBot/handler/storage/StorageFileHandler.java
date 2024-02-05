package com.nekit.MusicBot.handler.storage;

import com.nekit.MusicBot.enumBot.StateBot;
import com.nekit.MusicBot.handler.Handler;
import com.nekit.MusicBot.model.FileEntity;
import com.nekit.MusicBot.service.FileService;
import com.nekit.MusicBot.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import static com.nekit.MusicBot.util.TelegramUtil.sendDocVidAudPhoto;

@Component
@Slf4j
@RequiredArgsConstructor
public class StorageFileHandler implements Handler {

    private final FileService fileService;
    private final UserService userService;

    @Override
    public StateBot getCurrentState() {
        return StateBot.STORAGE_FILE;
    }

    @Override
    public Object handle(Message message) {
        if (!NumberUtils.isCreatable(message.getText())) {
            return null;
        }
        FileEntity entity = fileService.findById(Long.valueOf(message.getText()));
        logSelectedButton(message, entity);
        userService.updateUserState(message.getChatId(), entity.getFileLesson().getStateBot());

        return sendDocVidAudPhoto(message.getChatId(), entity, null, getCurrentState());
    }

    private static void logSelectedButton(Message message, FileEntity fileEntity) {
        log.info("User @{}:{} select button: {}", message.getChat().getUserName(),
                message.getChat().getFirstName(), fileEntity.getFileName());
    }
}

