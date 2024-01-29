package com.nekit.MusicBot.log;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Constant {

    public static final String EMPTY = "пусто \uD83D\uDE22";
    public static final String ERROR_WITH_SENDING_MESSAGE = "Error with sending message";
    public static final String ERROR_WITH_START_TELEGRAM_BOT_MESSAGE = "Error with initializing bot";
    public static final String ACCOUNT_BY_ID_NOT_FOUND_MESSAGE = "Account with id '%s' not found";

    public static final String PROFILE = """
            Профиль преподавателя:
            ---------------------------------
            ФИО 📋:
            %s
            ---------------------------------
            Контакты 📞:
            %s
            ---------------------------------
            Любимая вкусняшка 🍫:
            %s
            ---------------------------------
            О себе 📖:
            %s
            ---------------------------------
            """;
}
