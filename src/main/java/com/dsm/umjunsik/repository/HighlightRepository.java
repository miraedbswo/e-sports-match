package com.dsm.umjunsik.repository;

import com.dsm.umjunsik.model.entity.Highlight;
import com.dsm.umjunsik.model.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HighlightRepository extends JpaRepository<Highlight, Long> {
    List<Highlight> findByVideoNameContainingOrderByDatetimeDesc(String query);
    List<Highlight> findDistinctByTeamsInOrderByDatetimeDesc(List<Team> teams);
    Optional<Highlight> getHighlightById(Long videoId);
}
