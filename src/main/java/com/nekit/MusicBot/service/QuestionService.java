package com.nekit.MusicBot.service;

import com.nekit.MusicBot.model.QuestionEntity;
import com.nekit.MusicBot.model.TeacherEntity;
import com.nekit.MusicBot.model.UserEntity;
import com.nekit.MusicBot.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository repository;
    private final LetterSender letterSender;

    public void saveNewQuestionAll(UserEntity author, Message message) {
        QuestionEntity question = QuestionEntity.builder()
                .author(author)
                .teacher(null)
                .text(message.getText())
                .build();
        question = repository.save(question);
        letterSender.letterNewQuestionAll(question);
    }

    public void saveNewQuestionPersonallyWithoutText(UserEntity author, TeacherEntity teacher) {
        QuestionEntity question = QuestionEntity.builder()
                .author(author)
                .teacher(teacher)
                .build();
        repository.save(question);
    }

    public void addTextInLastQuestion(UserEntity user, String text) {
        QuestionEntity entity = repository.findFirstByAuthorOrderByCreatedAtDesc(user).orElseThrow();
        entity.setText(text);
        entity = repository.save(entity);
        letterSender.letterNewQuestionPersonally(entity);
    }

    public Map<Long, String> getQuestionMap(TeacherEntity teacher) {
        List<QuestionEntity> list = repository.findAllByTeacherAndAnswerIsNull(teacher);
        return list.stream()
                .filter(questionEntity -> Objects.nonNull(questionEntity.getText()))
                .collect(Collectors.toMap(QuestionEntity::getId, QuestionEntity::getText));
    }

    public QuestionEntity findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void selectQuestion(QuestionEntity question) {
        question.setSelectNumber(question.getSelectNumber() + 1);
        repository.save(question);
    }

    public void saveAnswerSelectedQuestion(TeacherEntity teacher, String answer) {
        QuestionEntity entity = repository.findFirstByTeacherOrderByUpdatedAtDesc(teacher).orElseThrow();
        entity.setAnswer(answer);
        repository.save(entity);
        letterSender.letterNewAnswer(entity);
    }
}
