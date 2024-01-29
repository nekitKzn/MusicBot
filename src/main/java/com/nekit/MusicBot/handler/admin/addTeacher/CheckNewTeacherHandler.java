package com.nekit.MusicBot.handler.admin.addTeacher;

import com.nekit.MusicBot.handler.Handler;
import com.nekit.MusicBot.model.UserEntity;
import com.nekit.MusicBot.service.LetterSender;
import com.nekit.MusicBot.service.TeacherService;
import com.nekit.MusicBot.service.UserService;
import com.nekit.MusicBot.state.StateBot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Objects;

import static com.nekit.MusicBot.state.StateBot.ADMIN_MAIN;

@Component
@RequiredArgsConstructor
public class CheckNewTeacherHandler implements Handler {

    private final static String SUCCESS_MESSAGE = "Пользователь с таким именем найден успешно. \uD83D\uDD25 " +
            "Добавляем нового преподавателя в систему! Теперь у него будет доступна админка преподавателя и" +
            " он сможет принимать анонимные вопросы от курсантов";
    private final static String FAILED_NOT_FOUND_MESSAGE = "Пользователь не найден! \uD83D\uDE14 Попробйте ввести правильный username человека. " +
            "Возможно этот человек не ввел свой userName в настройках телеграмма. " +
            "Учтите что, добавляемый вами преподаватель должен активировать данного бота (зайти и просто нажать start), " +
            "иначе он не сможет заполнять даные о себе, а также принимать анонимные вопросы от курсантов";

    private final static String FAILED_EXIST_MESSAGE = "Этот пользователь уже добавлен в систему как преподаватель, введите другого";

    private final UserService userService;
    private final TeacherService teacherService;
    private final LetterSender letterSender;

    @Override
    public StateBot getCurrentState() {
        return StateBot.CHECK_NEW_TEACHER;
    }

    @Override
    public BotApiMethod<?> handle(Message message) {
        UserEntity user = userService.findByUserName(message.getText());
        String text;

        var keyboard = getKeyboardWithOneButton(ADMIN_MAIN);

        if (Objects.isNull(user)) {
            text = FAILED_NOT_FOUND_MESSAGE;
        } else if (Objects.nonNull(teacherService.findByTelegramId(user.getTelegramId()))) {
            text = FAILED_EXIST_MESSAGE;
        } else {
            userService.updateUserState(message.getChatId(), StateBot.CREATE_TEACHER_SUCCESSFULLY);
            teacherService.saveNewTeacher(user);
            letterSender.letterNewTeacher(user);
            text = SUCCESS_MESSAGE;
        }

        return SendMessage.builder()
                .chatId(message.getChatId())
                .text(text)
                .replyMarkup(keyboard)
                .build();
    }
}
