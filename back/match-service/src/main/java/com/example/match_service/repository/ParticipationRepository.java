package com.example.match_service.repository;

import com.example.match_service.domain.Participation;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipationRepository extends JpaRepository<Participation,Long> {
    Optional<Participation> findByMatchIdAndUserId(Long matchId, UUID userId);

    List<Participation> findAllByMatchId(@NotNull Long matchId);
    List<Participation> findAllByUserId(@NotNull UUID userId);

}
