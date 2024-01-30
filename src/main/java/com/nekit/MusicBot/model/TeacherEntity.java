package com.nekit.MusicBot.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import java.util.Objects;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@Entity
@Table(name = "teachers", schema = "public")
public class TeacherEntity extends AbstractEntity {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_telegram_id")
    private UserEntity user;

    @Column(name = "photo_id")
    private String photo;

    private String fio;

    private String info;

    private String chocolate;

    private String contacts;

    public String getName() {
        return Objects.nonNull(fio) ? fio : user.getTelegramFirstName();
    }

}
