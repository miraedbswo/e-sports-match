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

    @Query("select v.team.id as teamId, count(v) as voteCount " +
            "from Vote v " +
            "where v.match = :match " +
            "group by v.team")
    List<VoteStatistic> findVoteCount(@Param("match") Match match);
}
