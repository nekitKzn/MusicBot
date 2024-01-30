package com.nekit.MusicBot.service;

import com.nekit.MusicBot.handler.Handler;
import com.nekit.MusicBot.state.StateBot;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.EnumMap;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
@RequiredArgsConstructor
public class UpdateHandler {

    private static final String ADMIN_COMMAND = "/admin";
    private static final String TEACHER_COMMAND = "/teacher";

    private final UserService userService;
    private final TeacherService teacherService;

    private final List<Handler> handlers;
    private final EnumMap<StateBot, Handler> stateToHandlerMap = new EnumMap<>(StateBot.class);

    @PostConstruct
    public void postConstruct() {
        handlers.forEach(handler -> stateToHandlerMap.put(handler.getCurrentState(), handler));
    }

    public Object handleMessage(Update update) {

        if (isMessageWithText(update)) { // пришел текст

            Message message = update.getMessage();
            Long telegramUserId = update.getMessage().getFrom().getId();
            String userName = message.getFrom().getUserName();
            String firstName = message.getFrom().getFirstName();

            if (adminCommand(message)) {
                userService.updateUserState(message.getFrom().getId(), StateBot.ADMIN_MAIN);
            }
            if (teacherCommand(message)) {
                userService.updateUserState(message.getFrom().getId(), StateBot.TEACHER_MAIN);
            }

            StateBot state = userService.getUserState(telegramUserId, message);
            log.info("User @{} : {} sent '{}'. State is {}", userName, firstName, message.getText(), state.name());
            return stateToHandlerMap.get(state).handle(message);

        } else if (update.hasCallbackQuery()) { //нажата кнопка

            Long telegramUserId = update.getCallbackQuery().getFrom().getId();
            StateBot state = StateBot.getStateBotByCallBackQuery(update.getCallbackQuery().getData());

            if (Objects.nonNull(state)) { // значит нажата кнопка
                log.info("User @{}:{} pressed button '{}'", update.getCallbackQuery().getFrom().getUserName(),
                        update.getCallbackQuery().getFrom().getFirstName(),
                        state.getButtonInThisState());
                userService.updateUserState(telegramUserId, state);
                return stateToHandlerMap.get(state).handle((Message) update.getCallbackQuery().getMessage());

            } else { // значит выбран как либо варитант
                StateBot stateBot = userService.findByTelegramId(telegramUserId).getState();
                log.info("User @{}:{} select button with id: {}", update.getCallbackQuery().getFrom().getUserName(),
                        update.getCallbackQuery().getFrom().getFirstName(), update.getCallbackQuery().getData());
                StateBot newState = stateToHandlerMap.get(stateBot).getNextState();
                userService.updateUserState(telegramUserId, newState);
                Message message = (Message) update.getCallbackQuery().getMessage();
                message.setText(update.getCallbackQuery().getData());
                return stateToHandlerMap.get(newState).handle(message);
            }

        } else if (isPhoto(update)) { // отправлена фотка
            log.info("Пришло фото");
            Long telegramUserId = update.getMessage().getFrom().getId();
            StateBot stateBot = userService.findByTelegramId(telegramUserId).getState();
            StateBot newState = stateToHandlerMap.get(stateBot).getNextState();
            if (Objects.nonNull(newState)) {
                userService.updateUserState(telegramUserId, newState);
                return stateToHandlerMap.get(newState).handle(update.getMessage());
            }
        }
        return null;
    }

    private boolean adminCommand(Message message) {
        return message.hasEntities() && ADMIN_COMMAND.equals(message.getText())
                && userService.checkIsAdmin(message.getFrom().getId());
    }

    private boolean teacherCommand(Message message) {
        return message.hasEntities() && TEACHER_COMMAND.equals(message.getText())
                && Objects.nonNull(teacherService.findByTelegramId(message.getFrom().getId()));
    }

    private boolean isMessageWithText(Update update) {
        return !update.hasCallbackQuery() && update.hasMessage() && update.getMessage().hasText();
    }

    private boolean isPhoto(Update update) {
        return update.hasMessage() && update.getMessage().hasPhoto();
    }
}
