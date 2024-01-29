package com.nekit.MusicBot.repository;

import com.nekit.MusicBot.model.QuestionEntity;
import com.nekit.MusicBot.model.TeacherEntity;
import com.nekit.MusicBot.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {

    Optional<QuestionEntity> findFirstByAuthorOrderByCreatedAtDesc(UserEntity author);

    List<QuestionEntity> findAllByTeacherAndAnswerIsNull(TeacherEntity teacher);

    Optional<QuestionEntity> findFirstByTeacherAndSelectedIsTrueOrderByCreatedAtDesc(TeacherEntity teacher);
}