package com.nekit.MusicBot.exception;

import static org.apache.commons.lang3.ObjectUtils.isEmpty;

public class MusicBotException extends RuntimeException {

    public MusicBotException(String message, Object... args) {
        super(getErrorMessage(message, args));
    }

    public MusicBotException(String message, Throwable e) {
        super(message, e);
    }

    private static String getErrorMessage(String message, Object... args) {
        return isEmpty(args) ? message : String.format(message, args);
    }
}
