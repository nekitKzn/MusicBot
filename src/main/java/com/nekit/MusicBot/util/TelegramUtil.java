package com.nekit.MusicBot.util;

import com.nekit.MusicBot.model.TeacherEntity;
import com.nekit.MusicBot.state.StateBot;
import lombok.experimental.UtilityClass;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Objects;

import static com.nekit.MusicBot.log.Constant.EMPTY;
import static com.nekit.MusicBot.log.Constant.PROFILE;

@UtilityClass
public class TelegramUtil {


    public static InlineKeyboardButton createButtonByState(StateBot state) {
        return InlineKeyboardButton.builder()
                .text(state.getButtonInThisState())
                .callbackData(state.name())
                .build();
    }

    public static InlineKeyboardButton button(String text, Long callback) {
        return InlineKeyboardButton.builder()
                .text(text)
                .callbackData(Long.toString(callback))
                .build();
    }

    public static InlineKeyboardButton createButtonWithUrl(String text, String command, String url) {
        return InlineKeyboardButton.builder()
                .text(text)
                .url(url)
                .callbackData(command)
                .build();
    }

    public static String getStringProfileByTeacherEntity(TeacherEntity entity) {
        return String.format(PROFILE,
                Objects.isNull(entity.getFio()) ? EMPTY : entity.getFio(),
                Objects.isNull(entity.getContacts()) ? EMPTY : entity.getContacts(),
                Objects.isNull(entity.getChocolate()) ? EMPTY : entity.getChocolate(),
                Objects.isNull(entity.getInfo()) ? EMPTY : entity.getInfo());
    }


}
