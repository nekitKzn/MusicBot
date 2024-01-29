package com.nekit.MusicBot.repository;

import com.nekit.MusicBot.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByTelegramUserName(String userName);

    Optional<UserEntity> findByTelegramId(Long telegramId);

    boolean existsByTelegramIdAndAdminIsTrue(Long telegramId);

}
