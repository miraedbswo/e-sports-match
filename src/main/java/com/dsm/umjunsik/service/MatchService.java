package com.dsm.umjunsik.service;

import com.dsm.umjunsik.model.request.VoteRequest;
import com.dsm.umjunsik.model.response.LikeResponse;
import com.dsm.umjunsik.model.response.MatchResponseList;
import com.dsm.umjunsik.model.response.VoteResponse;

public interface MatchService {
    MatchResponseList getMatches();

    VoteResponse getVoteCounts(Long matchId);

    VoteResponse createVote(Long matchId, VoteRequest data);

    LikeResponse getLikeCounts(Long matchId);

    LikeResponse createLike(Long matchId);
}
