package com.nekit.MusicBot.state;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum StateBot {


    /**
     * USER STATE
     */
    START("""
            Привет! ✋ Это бот сессии «Призван служить». \s

             Через меня можно задать анонимный вопрос преподавателям 😜, узнать о них информацию 😉, скачать ноты по хору и сольфеджио 🎶, получить консультацию по домашке 😋""",
            "Вернуться на главную ↩️"),

    ABOUT_BOT("""
            Региональная сессия 'призван служить', проводим шикарные курсы в течении последних 10 лет Все четко, залетайте к нам\s
               Маша, сюда нужно красивое описание того что из себя представляет эта организация "призван служить, напишши меня, сюда ее добавлю
               🎶
            \s""", "О нас \uD83D\uDCD6"),


    USER_TEACHER_LIST("""
            Знакомтесь!
            Ваши преподаватели:

            """, "Преподаватели \uD83D\uDD25"),
    USER_TEACHER_PROFILE("%s", null),


    CREATE_QUESTION("""
            Хочешь задать анонимный вопрос конкретно преподавателю? Или общий вопрос всем?
            """, "Анонимный вопрос ❓"),

    CREATE_QUESTION_ALL("""
            Вопрос отправится в группу организаторов и преподавателей. Ответ на вопрос будет в канале.
                        
            Напиши свой вопрос одним предложением:
            """, "Всем"),
    CREATE_QUESTION_ALL_SUCCESSFULLY("""
            Спасибо за ваш вопрос, %s, мы постараемся максимально оперативно на него ответить!
            Ответ скорее всего будет в канале, следите за новостями 📰
            """, null),


    CREATE_QUESTION_PERSONALLY("""
            Выбери преподавателя для вопроса, он не узнает отправителя и пришлет тебе ответ через меня)
            """, "Лично преподавателю"),
    CREATE_QUESTION_PERSONALLY_WRITE("""
            Напиши свой вопрос, он будет доставен лично преподавателяю:
            """, ""),
    CREATE_QUESTION_PERSONALLY_SUCCESSFULLY("""
            Спасибо за ваш вопрос, он уже отправлен преподавателю.
            Ответ я пришлю тебе здесь, лично 🤫
            """, null),


    /**
     * TEACHER STATE
     */
    TEACHER_MAIN("\uD83E\uDE84\uD83E\uDE84\uD83E\uDE84Панель преподавателя!" +
            "\uD83E\uDE84\uD83E\uDE84\uD83E\uDE84", "⬅️"),
    QUESTION_LIST("""
             Это анонимные вопросы, адресованные Вам, на них курсанты хотят получить ответ!
             
             %s
            """,
            "Неотвеченные вопросы"),
    CREATE_ANSWER("""
            Вопрос звучал так:
                        
            «%s»
                        
            Напишите здесь ответ. Он сразу уйдет отправителю.
            """, null),
    CREATE_ANSWER_SUCCESSFULLY("""
            Спасибо за ваш ответ, он уже перенаправлен отправителю.
            """, null),
    TEACHER_EDIT("""
            Здесь вы можете редактировать информацию о себе, выберите нужную кнопочку и обновите информацию\\!\s
            \s
            %s
            """, "Мой профиль"),
    TEACHER_EDIT_PHOTO("Пришлите фотографию для профиля", "Загрузить фото \uD83D\uDCF7"),
    TEACHER_EDIT_PHOTO_SUCCESSFULLY("Фотография профиля загружена", null),
    TEACHER_EDIT_FIO("Напишите ФИО:", "Изменить ФИО"),
    TEACHER_EDIT_FIO_SUCCESSFULLY("Информация ФИО обновлена успешно", null),
    TEACHER_EDIT_CONTACTS("Напишите контакты:", "Изменть контакты"),
    TEACHER_EDIT_CONTACTS_SUCCESSFULLY("Информация о контактах обновлена успешно", null),
    TEACHER_EDIT_CHOCOLATE("Напишите свою любимую вкусняшку:", "Изменить вкусняшку"),
    TEACHER_EDIT_CHOCOLATE_SUCCESSFULLY("Информация о вкусняшке обновлена успешно", null),
    TEACHER_EDIT_INFO("Напишите 'О себе'", "Изменить 'О себе'"),
    TEACHER_EDIT_INFO_SUCCESSFULLY("Информация 'О себе' обновлена успешно", null),


    /**
     * ADMIN STATE
     */
    ADMIN_MAIN("\uD83E\uDE84\uD83E\uDE84\uD83E\uDE84Панель администратора!" +
            "\uD83E\uDE84\uD83E\uDE84\uD83E\uDE84", "⬅️"),


    LIST_USERS("Список пользователей", "Пользователи"),

    ADMIN_TEACHER_LIST("Список преподавателей: ", "Преподаватели"),


    CREATE_NEW_ADMIN("Для того, чтобы добавить нового админстратора в систему, введите имя пользователя телеграмма " +
            "без симовла @  \n Учтите, ему будет доступн полный функционал бота, добавляйте толкьо довереных лиц!",
            "Добавить администратора"),
    CHECK_NEW_ADMIN("%s", null),
    CREATE_ADMIN_SUCCESSFULLY("Администратор успешно добавлен в систему", null),


    CREATE_NEW_TEACHER("Для того, чтобы добавить нового преподавателя в систему, введите имя пользователя телеграмма " +
            "без симовла @",
            "Добавить преподавателя"),
    CHECK_NEW_TEACHER("%s", null),
    CREATE_TEACHER_SUCCESSFULLY("Преподаватель успешно добавлен в систему", null);

    private final String message;
    private final String buttonInThisState;

    public static StateBot getStateBotByCallBackQuery(String query) {
        return Arrays.stream(values())
                .filter(state -> query.equals(state.name()))
                .findFirst()
                .orElse(null);
    }
}
