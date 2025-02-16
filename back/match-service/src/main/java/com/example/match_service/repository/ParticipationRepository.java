package com.example.match_service.repository;

import com.example.match_service.domain.Participation;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipationRepository extends JpaRepository<Participation,Long> {
    Optional<Participation> findByMatchIdAndUserId(Long matchId, UUID userId);
}
