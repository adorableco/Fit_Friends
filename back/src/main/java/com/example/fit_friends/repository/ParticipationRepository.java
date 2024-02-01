package com.example.fit_friends.repository;

import com.example.fit_friends.domain.Match;
import com.example.fit_friends.domain.Participation;
import com.example.fit_friends.domain.User;
import jakarta.servlet.http.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {
    Optional<Participation> findByMatchAndUser(Match match, User user);

    List<Participation> findByUser(User user);

    @Query("select count(p.participationId) from Participation p join Match m on p.match = m join User u on p.user = u where m.endTime < :now and u.userId = :userId")
    Float countMyEndMatches (LocalDateTime now, Long userId);

    @Query("select count(p.participationId) from Participation p join User u on p.user = u where u.userId = :userId and p.attendance = true")
    Float countMyPresentedMatches (Long userId);

}
