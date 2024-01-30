package com.nekit.MusicBot.handler.admin;

import com.nekit.MusicBot.handler.Handler;
import com.nekit.MusicBot.model.UserEntity;
import com.nekit.MusicBot.service.UserService;
import com.nekit.MusicBot.state.StateBot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Comparator;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AdminListUsersHandler implements Handler {

    private final UserService userService;

    @Override
    public StateBot getCurrentState() {
        return StateBot.ADMIN_LIST_USERS;
    }

    @Override
    public Object handle(Message message) {
        var keyboard = getKeyboardWithOneButton(StateBot.ADMIN_MAIN);
        var users = userService.getAllUsers();
        String text = users.stream()
                .sorted(Comparator.comparingLong(UserEntity::getCountChangeState).reversed())
                .map(user -> String.format("%s %s| @%s - %s",
                        user.getCountChangeState(),
                        getNumberSpase(user.getCountChangeState()),
                        user.getTelegramUserName(),
                        user.getTelegramFirstName()))
                .collect(Collectors.joining("\n"));
        return getDefaultMessage(message, keyboard, text);
    }

    private static String getNumberSpase(Long number) {
        if (number == 0) {
            return " ".repeat(8);
        } else {
            return " ".repeat(10 - ((int) Math.log10(number) + 1) * 2);
        }
    }
}
