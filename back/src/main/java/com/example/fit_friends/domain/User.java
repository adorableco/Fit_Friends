package com.example.fit_friends.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="User_id",updatable = false)
    private Long userId;

    @Column(name="Name", nullable = false)
    private String name;

    @Column(name="Email")
    private String email;

    @Column(name="Sex")
    private char gender;

    @Column(name = "Age")
    private String age;

    @Column(name="Sex_visible")
    @ColumnDefault("1")
    private boolean genderVisible;

    @Column(name="Age_visible")
    @ColumnDefault("1")
    private boolean ageVisible;

    @Column(name = "winningRate")
    private float winningRate;

    @Column(name = "Late_rate")
    private float lateRate;

    @Builder
    public User(Long userId, String name, String email,
                char gender, String age, boolean genderVisible,
                boolean ageVisible, float winningRate, float lateRate) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.age = age;
        this.genderVisible = genderVisible;
        this.ageVisible = ageVisible;
        this.winningRate = winningRate;
        this.lateRate = lateRate;
    }


}
