
package com.nekit.MusicBot.handler;

import com.nekit.MusicBot.state.StateBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.Arrays;
import java.util.List;

import static com.nekit.MusicBot.util.TelegramUtil.createButtonByState;
import static org.apache.commons.lang3.ObjectUtils.isEmpty;

public interface Handler {

    StateBot getCurrentState();

    default StateBot getNextState() {
        return null;
    }

    Object handle(Message message);

    default BotApiMethod<?> getDefaultMessage(Message message, InlineKeyboardMarkup keyboard, Object... args) {
        String text = isEmpty(args) ? getCurrentState().getMessage() : String.format(getCurrentState().getMessage(), args);
        if (message.hasReplyMarkup()) {
            return EditMessageText.builder()
                    .messageId(message.getMessageId())
                    .chatId(message.getChatId())
                    .text(text)
                    .replyMarkup(keyboard)
                    .build();
        } else {
            return SendMessage.builder()
                    .chatId(message.getChatId())
                    .text(text)
                    .replyMarkup(keyboard)
                    .build();
        }
    }

    default InlineKeyboardMarkup getKeyboardDefault(StateBot... stateBot) {
        return InlineKeyboardMarkup.builder().keyboard(Arrays.stream(stateBot)
                .map(state -> List.of(createButtonByState(state)))
                .toList()).build();
    }
}
