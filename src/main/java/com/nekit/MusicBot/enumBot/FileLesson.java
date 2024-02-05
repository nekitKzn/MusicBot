package com.nekit.MusicBot.enumBot;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FileLesson {
    CHOIR_FILE(StateBot.STORAGE_CHOIR),
    ORCHESTRA_FILE(StateBot.STORAGE_ORCHESTRA),
    SOLFEGGIO_FILE(StateBot.STORAGE_SOLFEGGIO),
    OTHER_FILE(StateBot.STORAGE_OTHER);

    private final StateBot stateBot;
}
