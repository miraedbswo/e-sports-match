package com.dsm.umjunsik.repository;

import com.dsm.umjunsik.model.entity.Highlight;
import com.dsm.umjunsik.model.entity.Like;
import com.dsm.umjunsik.model.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Integer countByMatch(Match match);
    Integer countByHighlight(Highlight highlight);
}
