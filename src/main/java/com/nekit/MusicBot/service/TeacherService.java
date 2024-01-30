package com.nekit.MusicBot.service;

import com.nekit.MusicBot.model.TeacherEntity;
import com.nekit.MusicBot.model.UserEntity;
import com.nekit.MusicBot.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository repository;

    public TeacherEntity findById(Long id) {
        return repository.findById(id).orElse(null);
    }


    @Transactional
    public void saveNewTeacher(UserEntity entity) {
        TeacherEntity teacher = TeacherEntity.builder()
                .user(entity).build();
        repository.save(teacher);
    }

    public TeacherEntity findByTelegramId(Long telegramId) {
        return repository.findByUserTelegramId(telegramId).orElse(null);
    }

    public Map<Long, String> getTeacherMap() {
        return repository.findAll().stream()
                .collect(Collectors.toMap(TeacherEntity::getId, TeacherEntity::getName));
    }

    @Transactional
    public void updateInfo(Message message) {
        var entity = repository.findByUserTelegramId(message.getChatId()).orElseThrow();
        entity.setInfo(message.getText());
        repository.save(entity);
    }

    @Transactional
    public void updateFio(Message message) {
        var entity = repository.findByUserTelegramId(message.getChatId()).orElseThrow();
        entity.setFio(message.getText());
        repository.save(entity);
    }

    @Transactional
    public void updateChocolate(Message message) {
        var entity = repository.findByUserTelegramId(message.getChatId()).orElseThrow();
        entity.setChocolate(message.getText());
        repository.save(entity);
    }

    @Transactional
    public void updateContacts(Message message) {
        var entity = repository.findByUserTelegramId(message.getChatId()).orElseThrow();
        entity.setContacts(message.getText());
        repository.save(entity);
    }

    @Transactional
    public void updatePhoto(Message message) {
        var entity = repository.findByUserTelegramId(message.getChatId()).orElseThrow();
        PhotoSize photo = message.getPhoto().get(message.getPhoto().size() - 1);
        entity.setPhoto(photo.getFileId());
        repository.save(entity);
    }
}
