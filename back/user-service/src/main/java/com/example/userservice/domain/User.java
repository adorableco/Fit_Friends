package com.example.userservice.domain;

import com.example.userservice.dto.GameResult;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId;

    private String name;

    private String email;

    @Column(nullable = false)
    private String picture;

    @Enumerated(EnumType.STRING)
    private Role role;

    private char gender;

    private String age;

    private String level;

    private int matchCount = 0;

    private int winCount = 0;

    private String accessToken;

    @ColumnDefault("1")
    private boolean genderVisible;

    @ColumnDefault("1")
    private boolean ageVisible;

    private double winningRate = 0.0;

    private double attendanceRate = 0.0;

    public User update(String name, String picture){
        this.name = name;
        this.picture = picture;

        return this;
    }

    public void updateWinningRate(GameResult gameResult){
        this.matchCount++;
        if(gameResult.equals(GameResult.WIN)) this.winCount++;
        this.winningRate = Math.round(((double) this.winCount / this.matchCount * 100) * 100) / 100.0;
    }

    @Builder
    public User(UUID userId, String name, String email, String picture,
                Role role, char gender, String age, String level, String accessToken,
                boolean genderVisible, boolean ageVisible) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
        this.gender = gender;
        this.age = age;
        this.level = level;
        this.accessToken = accessToken;
        this.genderVisible = genderVisible;
        this.ageVisible = ageVisible;
    }
}
