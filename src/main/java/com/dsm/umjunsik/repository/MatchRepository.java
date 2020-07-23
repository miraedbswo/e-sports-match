package com.dsm.umjunsik.repository;

import com.dsm.umjunsik.model.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
    Optional<Match> getMatchById(Long id);
    List<Match> getAllByDatetimeBetweenOrderById(LocalDateTime now, LocalDateTime tomorrow);
}
