package com.nekit.MusicBot.handler.admin;

import com.nekit.MusicBot.enumBot.FunctionBot;
import com.nekit.MusicBot.enumBot.StateBot;
import com.nekit.MusicBot.handler.Handler;
import com.nekit.MusicBot.model.UserEntity;
import com.nekit.MusicBot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.nekit.MusicBot.enumBot.StateBot.ADMIN_MAIN;
import static com.nekit.MusicBot.util.TelegramUtil.*;

@Component
@RequiredArgsConstructor
public class AdminListUserUpdateCountHandler implements Handler {

    private final UserService userService;

    @Override
    public StateBot getCurrentState() {
        return StateBot.ADMIN_LIST_USERS_UPDATE_COUNT;
    }

    @Override
    public Object handle(Message message) {
        var keyboard = InlineKeyboardMarkup.builder()
                .keyboard(
                        List.of(
                                List.of(createButtonByFunction(FunctionBot.RESET_COUNT)),
                                List.of(createButtonByState(ADMIN_MAIN))
                        ))
                .build();
        var users = userService.getAllUsers();
        String text = users.stream()
                .sorted(Comparator.comparingLong(UserEntity::getCountChangeState).reversed())
                .map(user -> String.format("%s %s| %s%s",
                        user.getCountChangeState(),
                        getNumberSpase(user.getCountChangeState()),
                        Objects.isNull(user.getTelegramUserName()) ? "" : "@" + user.getTelegramUserName() + " ",
                        user.getTelegramFirstName()))
                .collect(Collectors.joining("\n"));
        return getDefaultMessage(message, keyboard, text);
    }
}
