package com.dsm.umjunsik.controller;

import com.dsm.umjunsik.model.request.VoteRequest;
import com.dsm.umjunsik.model.response.LikeResponse;
import com.dsm.umjunsik.model.response.MatchResponseList;
import com.dsm.umjunsik.model.response.VoteResponse;
import com.dsm.umjunsik.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/match")
@RequiredArgsConstructor
public class MatchController {
    private final MatchService matchService;

    @GetMapping("/schedule")
    public MatchResponseList getMatchSchedules() {
        return matchService.getMatches();
    }

    @GetMapping("/vote/{matchId}")
    public VoteResponse getVoteCounts(@PathVariable Long matchId) {
        return matchService.getVoteCounts(matchId);
    }

    @PostMapping("/vote/{matchId}")
    public VoteResponse createVote(@PathVariable Long matchId, @RequestBody @Valid VoteRequest data) {
        return matchService.createVote(matchId, data);
    }

    @GetMapping("/like/{matchId}")
    public LikeResponse getLikeCounts(@PathVariable Long matchId) {
        return matchService.getLikeCounts(matchId);
    }

    @PostMapping("/like/{matchId}")
    public LikeResponse createLike(@PathVariable Long matchId) {
        return matchService.createLike(matchId);
    }
}
