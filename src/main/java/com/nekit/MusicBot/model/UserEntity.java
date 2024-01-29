package com.nekit.MusicBot.model;

import com.nekit.MusicBot.state.StateBot;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@Entity
@Table(name = "users", schema = "public")
public class UserEntity extends AbstractEntity {

    @Id
    private Long telegramId;

    private String telegramUserName;

    private String telegramFirstName;

    @Enumerated(EnumType.STRING)
    private StateBot state;

    @Builder.Default
    private Boolean admin = false;
}
