package com.example.fit_friends.repository;

import com.example.fit_friends.domain.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface MatchRepository extends JpaRepository<Match, Long> {
    @Query("select count(m.matchId) from Match m join Participation p on m = p.match join User u on p.user = u  where u.userId = :userId and m.startTime <= :endTime and m.endTime >= :startTime")

    int findDuplicateMatch(Long userId, Date startTime, Date endTime);

}
