package com.example.fit_friends.repository;

import com.example.fit_friends.domain.Match;
import com.example.fit_friends.domain.Participation;
import com.example.fit_friends.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParticipationRespository extends JpaRepository<Participation, Long> {
    Optional<Participation> findByMatchAndUser(Match match, User user);
}
