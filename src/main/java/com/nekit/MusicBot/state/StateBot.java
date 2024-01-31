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
            "Призван Служить" - это региональная межцерковная сессия. 👨‍🎓👩‍🎓\s
                        
            Наша цель - развитие музыкальной культуры в церквях ⛪️. У нас Вы найдёте площадку для обмена опытом ✅, ободрения и поддержки в служении 🙋.
                        
            Этот бот поможет Вам узнать некоторую информацию о Сессии, преподавателях и организаторах, а также задать интересующий Вас вопрос о музыкальном служении.
               
            Copyright 2024 © All rights reserved
            \s""", "О нас \uD83D\uDCD6"),


    USER_TEACHER_LIST("""
            Знакомьтесь! 🙌
            Ваши преподаватели и организаторы курсов:

            """, "Руководство \uD83D\uDD25"),
    USER_TEACHER_PROFILE("%s", null),


    CREATE_QUESTION("""
            Хочешь задать анонимный вопрос лично преподавателю? Или общий вопрос всем?
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
            """, "Лично"),
    CREATE_QUESTION_PERSONALLY_WRITE("""
            Напиши свой вопрос, он будет доставен лично преподавателяю:
            """, ""),
    CREATE_QUESTION_PERSONALLY_SUCCESSFULLY("""
            Спасибо за ваш вопрос, он уже отправлен преподавателю.
            Ответ я пришлю тебе здесь, лично 🤫
            """, null),


    STORAGE("Здесь вы можете найти ноты, пособия и другие материалы по различным дисциплинам! \uD83D\uDCD4",
            "Материалы сессии \uD83D\uDCD1"),
    STORAGE_CHOIR("""
            Ноты для хора
                        
            %s
            """, "Хор \uD83D\uDC68\u200D\uD83D\uDC68\u200D\uD83D\uDC67\u200D\uD83D\uDC67"),

    STORAGE_ORCHESTRA("""
            Ноты для оркестра
                        
            %s
            """, "Оркестр \uD83C\uDFBA"),
    STORAGE_SOLFEGGIO("""
            Материалы для сольфеджио
                        
            %s
            """, "Сольфеджио \uD83C\uDFBC"),
    STORAGE_OTHER("""
            Другие материалы
                        
            %s
            """, "Другое \uD83D\uDCC1"),


    /**
     * TEACHER STATE
     */
    TEACHER_MAIN("\uD83E\uDE84Панель преподавателя!" +
            "\uD83E\uDE84", "⬅️"),
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
            Здесь вы можете редактировать информацию о себе, выберите нужную кнопочку и обновите информацию!\s
            \s
            %s
            """, "Мой профиль"),
    TEACHER_EDIT_PHOTO("Пришлите фотографию для профиля", "Загрузить фото \uD83D\uDCF7"),
    TEACHER_EDIT_PHOTO_SUCCESSFULLY("Фотография профиля загружена", null),
    TEACHER_EDIT_FIO("Напишите ФИО:", "Изменить ФИО"),
    TEACHER_EDIT_FIO_SUCCESSFULLY("Информация ФИО обновлена успешно", null),
    TEACHER_EDIT_CONTACTS("Напишите контакты:", "Изменить контакты"),
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


    ADMIN_LIST_USERS_ALL_COUNT("""
            Список пользователей общим количеством дейстивий:
                    
            %s
            """, "Пользователи ALl"),

    ADMIN_LIST_USERS_UPDATE_COUNT("""
            Список пользователей со сбрасываемым количеством действий:
                    
            %s
            """, "Пользователи Time"),

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
