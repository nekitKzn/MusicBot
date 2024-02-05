package com.nekit.MusicBot.handler.admin;

import com.nekit.MusicBot.enumBot.StateBot;
import com.nekit.MusicBot.handler.Handler;
import com.nekit.MusicBot.model.UserEntity;
import com.nekit.MusicBot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.nekit.MusicBot.enumBot.StateBot.ADMIN_MAIN;
import static com.nekit.MusicBot.util.TelegramUtil.getNumberSpase;

@Component
@RequiredArgsConstructor
public class AdminListUserAllCountHandler implements Handler {

    private final UserService userService;

    @Override
    public StateBot getCurrentState() {
        return StateBot.ADMIN_LIST_USERS_ALL_COUNT;
    }

    @Override
    public Object handle(Message message) {
        var keyboard = getKeyboardDefault(ADMIN_MAIN);
        var users = userService.getAllUsers();
        String text = users.stream()
                .sorted(Comparator.comparingLong(UserEntity::getCountChangeStateAll).reversed())
                .map(user -> String.format("%s %s| %s%s",
                        user.getCountChangeStateAll(),
                        getNumberSpase(user.getCountChangeStateAll()),
                        Objects.isNull(user.getTelegramUserName()) ? "" : "@" + user.getTelegramUserName() + " ",
                        user.getTelegramFirstName()))
                .collect(Collectors.joining("\n"));
        return getDefaultMessage(message, keyboard, text);
    }
}
