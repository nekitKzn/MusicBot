package com.nekit.MusicBot.service;

import com.nekit.MusicBot.enumBot.FileLesson;
import com.nekit.MusicBot.enumBot.FileType;
import com.nekit.MusicBot.model.FileEntity;
import com.nekit.MusicBot.model.UserEntity;
import com.nekit.MusicBot.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Triple;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.nekit.MusicBot.enumBot.FileType.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileService {

    private final FileRepository repository;
    private final UserService userService;

    @Transactional
    public FileEntity findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Transactional
    public void saveNewFile(Message message, FileLesson fileLesson) {
        var triple = defineFileType(message);

        UserEntity user = userService.findByTelegramId(message.getFrom().getId());
        FileEntity fileEntity = createFileEntity(triple, fileLesson, user);
        repository.save(fileEntity);
    }

    private FileEntity createFileEntity(Triple<FileType, String, String> triple, FileLesson fileLesson, UserEntity user) {
        return FileEntity.builder()
                .author(user)
                .fileType(triple.getLeft())
                .fileId(triple.getMiddle())
                .fileName(triple.getRight())
                .fileLesson(fileLesson)
                .build();
    }

    private Triple<FileType, String, String> defineFileType(Message message) {
        if (message.hasPhoto()) {
            var photo = message.getPhoto().get(message.getPhoto().size() - 1);
            return Triple.of(PHOTO, photo.getFileId(), "фото");
        }
        if (message.hasDocument()) {
            return Triple.of(DOCUMENT, message.getDocument().getFileId(), message.getDocument().getFileName());
        }
        if (message.hasVideo()) {
            return Triple.of(VIDEO, message.getVideo().getFileId(), message.getVideo().getFileName());
        }
        if (message.hasAudio()) {
            return Triple.of(AUDIO, message.getAudio().getFileId(), message.getAudio().getFileName());
        }
        return null;
    }

    @Transactional
    public void updateNameFile(Long userId, String nameFile) {
        var user = userService.findByTelegramId(userId);
        var file = repository.findFirstByAuthorOrderByCreatedAtDesc(user).orElse(null);
        if (Objects.nonNull(file)) {
            file.setFileName(nameFile);
            repository.save(file);
        }
    }

    @Transactional
    public Map<Long, FileEntity> getFileMap(FileLesson fileLesson) {
        return repository.findAllByFileLesson(fileLesson)
                .stream().collect(Collectors.toMap(FileEntity::getId, Function.identity()));
    }
}
