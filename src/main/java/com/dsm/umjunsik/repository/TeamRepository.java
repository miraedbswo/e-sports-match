package com.dsm.umjunsik.repository;

import com.dsm.umjunsik.model.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findAllByOrderByIdAsc();
    Optional<Team> getTeamById(Long id);
}
