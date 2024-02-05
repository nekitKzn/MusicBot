package com.nekit.MusicBot.repository;

import com.nekit.MusicBot.enumBot.FileLesson;
import com.nekit.MusicBot.model.FileEntity;
import com.nekit.MusicBot.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {

    Optional<FileEntity> findFirstByAuthorOrderByCreatedAtDesc(UserEntity user);

    List<FileEntity> findAllByFileLesson(FileLesson fileLesson);
}
