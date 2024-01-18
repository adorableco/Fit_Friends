package com.example.fit_friends.repository;

import com.example.fit_friends.domain.Participation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipationRespository extends JpaRepository<Participation, Long> {
}
