package com.nekit.MusicBot.service;

import com.nekit.MusicBot.exception.MusicBotException;
import com.nekit.MusicBot.model.UserEntity;
import com.nekit.MusicBot.repository.UserRepository;
import com.nekit.MusicBot.state.StateBot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Message;

import static com.nekit.MusicBot.log.Constant.ACCOUNT_BY_ID_NOT_FOUND_MESSAGE;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    @Transactional
    public StateBot getUserState(Long telegramUserId, Message message) {
        UserEntity userEntity = userRepository.findById(telegramUserId)
                .orElseGet(() -> userRepository.save(createUserEntity(message)));
        return userEntity.getState();
    }

    @Transactional
    public void updateUserState(Long telegramUserId, StateBot state) {
        UserEntity userEntity = userRepository.findById(telegramUserId)
                .orElseThrow(() -> new MusicBotException(ACCOUNT_BY_ID_NOT_FOUND_MESSAGE, telegramUserId));
        userEntity.setState(state);
        userRepository.save(userEntity);
    }

    public boolean checkIsAdmin(Long id) {
        return userRepository.existsByTelegramIdAndAdminIsTrue(id);
    }

    public UserEntity findByUserName(String userName) {
        return userRepository.findByTelegramUserName(userName)
                .orElse(null);
    }

    public UserEntity findByTelegramId(Long telegramId) {
        return userRepository.findByTelegramId(telegramId).orElse(null);
    }

    private UserEntity createUserEntity(Message message) {
        return UserEntity.builder()
                .telegramId(message.getFrom().getId())
                .telegramUserName(message.getFrom().getUserName())
                .telegramFirstName(message.getFrom().getFirstName())
                .state(StateBot.START)
                .build();
    }

    @Transactional
    public void saveAdmin(UserEntity user) {
        user.setAdmin(true);
        userRepository.save(user);
    }
}