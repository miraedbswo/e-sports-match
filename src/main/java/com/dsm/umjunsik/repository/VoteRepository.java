package com.dsm.umjunsik.repository;

import com.dsm.umjunsik.model.entity.Match;
import com.dsm.umjunsik.model.entity.Team;
import com.dsm.umjunsik.model.entity.Vote;
import com.dsm.umjunsik.model.response.VoteStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Integer countByMatchAndTeam(Match match, Team team);
    @Query("SELECT " +
            "    new VoteStatistic(v.team, COUNT(v)) " +
            "FROM " +
            "    Vote v " +
            "WHERE " +
            "    v.match = :match " +
            "GROUP BY " +
            "    v.team ")
    List<VoteStatistic> findVoteCount(@Param("name") Match match);
}
