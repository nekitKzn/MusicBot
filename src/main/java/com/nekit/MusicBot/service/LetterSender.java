package com.nekit.MusicBot.service;

import com.nekit.MusicBot.event.SendTelegramMessageEvent;
import com.nekit.MusicBot.model.QuestionEntity;
import com.nekit.MusicBot.model.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import static org.apache.commons.lang3.ObjectUtils.isEmpty;

@Service
@Slf4j
@RequiredArgsConstructor
public class LetterSender {

    private final ApplicationEventPublisher publisher;

    @Value("${bot.groupChatId}")
    private Long groupChatId;

    private final static String NEW_TEACHER_MESSAGE = """
            Приветствуем Вас, %s, Вас сделали преподавателем в боте 'Призван служить' 🎶.\s
            Теперь у вас доступна админка преподавателя через команду /teacher\s
                        
            Через нее вы сможете отвечать на анонимные сообщения от курсантов 😉, а также добавить добавить информацию о себе\s
            Благословений! С Богом! 🙏""";

    private final static String NEW_ADMIN_MESSAGE = """
            Приветствуем Вас, %s, Вас сделали администратором в боте 'Призван служить' 🎶.\s
            Теперь у вас доступна админка через команду /admin\s
                        
            Через нее вам доступен весь функционал системы""";

    private final static String NEW_QUESTION_ALL = """
            Поступил анонимный вопрос. ❓
            Содержание:
                        
            «%s»
            """;

    private final static String NEW_QUESTION_PERSONALLY = """
            Вам поступил анонимный вопрос от пользователя бота. ❓
            Содержание:
                        
            «%s»
                        
            Чтобы на него ответить вы дожны зайти в админку преподавателя с помощью команды: /teacher
            """;

    private final static String ANSWER = """
            Поступил ответ от преподавателя!
            %s 🔥
                        
            Ваш вопрос звучал так:
            «%s»
                        
            Ответ:
            «%s»
            """;

    public void letterNewAdmin(UserEntity user) {
        publish(user.getTelegramId(), NEW_ADMIN_MESSAGE, user.getTelegramFirstName());
    }

    public void letterNewTeacher(UserEntity user) {
        publish(user.getTelegramId(), NEW_TEACHER_MESSAGE, user.getTelegramFirstName());
    }

    public void letterNewQuestionAll(QuestionEntity question) {
        publish(groupChatId, NEW_QUESTION_ALL, question.getText());
    }

    public void letterNewQuestionPersonally(QuestionEntity question) {
        publish(question.getTeacher().getUser().getTelegramId(), NEW_QUESTION_PERSONALLY, question.getText());
    }

    public void letterNewAnswer(QuestionEntity entity) {
        publish(entity.getAuthor().getTelegramId(), ANSWER, entity.getTeacher().getName(), entity.getText(), entity.getAnswer());
    }

    private void publish(Long to, String template, Object... args) {
        String text = isEmpty(args) ? template : String.format(template, args);
        log.info("Письмо отправлено: " + text.replaceAll("\n", " "));
        publisher.publishEvent(new SendTelegramMessageEvent(this, text, to));
    }
}
