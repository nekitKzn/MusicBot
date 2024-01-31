package com.nekit.MusicBot.state;

import com.nekit.MusicBot.service.FunctionService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.function.Consumer;

import static com.nekit.MusicBot.state.StateBot.ADMIN_LIST_USERS_UPDATE_COUNT;

@Getter
@RequiredArgsConstructor
public enum FunctionBot {

    RESET_COUNT(FunctionService::resetCountToZero, ADMIN_LIST_USERS_UPDATE_COUNT, "Обнулить count");

    private final Consumer<FunctionService> function;
    private final StateBot stateBot;
    private final String buttonForThisFunction;

    public static FunctionBot getFunctionBotByCallBackQuery(String query) {
        return Arrays.stream(values())
                .filter(state -> query.equals(state.name()))
                .findFirst()
                .orElse(null);
    }
}
