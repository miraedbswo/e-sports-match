package com.dsm.umjunsik.service;

import com.dsm.umjunsik.exception.InvalidTokenException;
import com.dsm.umjunsik.exception.NoSuchElementException;
import com.dsm.umjunsik.model.entity.*;
import com.dsm.umjunsik.model.request.VoteRequest;
import com.dsm.umjunsik.model.response.*;
import com.dsm.umjunsik.repository.LikeRepository;
import com.dsm.umjunsik.repository.MatchRepository;
import com.dsm.umjunsik.repository.TeamRepository;
import com.dsm.umjunsik.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.LocalDateTime.now;


@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {

    private final UserService userService;

    private final LikeRepository likeRepository;
    private final TeamRepository teamRepository;
    private final MatchRepository matchRepository;
    private final VoteRepository voteRepository;

    @Override
    public MatchResponseList getMatches() {
        LocalDateTime now = now();
        LocalDateTime tomorrow = now().plusDays(1);

        List<MatchResponse> matchResponses = matchRepository.getAllByDatetimeBetweenOrderById(now, tomorrow).stream()
                .map(match -> new MatchResponse(
                        match,
                        voteRepository.countByMatchAndTeam(match, match.getRedTeam()),
                        voteRepository.countByMatchAndTeam(match, match.getBlueTeam()))
                ).collect(Collectors.toList());

        return new MatchResponseList(matchResponses);
    }

    @Override
    public VoteResponse getVoteCounts(Long matchId) {
        return matchRepository.getMatchById(matchId)
                .map(match -> {
                    List<VoteStatistic> voteCounts = voteRepository.findVoteCount(match);
                    VoteResponse voteResponse = new VoteResponse();

                    for (VoteStatistic vote : voteCounts) {
                        if (isRedTeam(match, vote)) {
                            voteResponse.setRedTeamVoteCount(vote.getTeamVoteCount());
                        } else {
                            voteResponse.setBlueTeamVoteCount(vote.getTeamVoteCount());
                        }
                    }

                    return voteResponse;
                }).get();
    }

    private boolean isRedTeam(Match match, VoteStatistic statistic) {
        return match.getRedTeam().getId().equals(statistic.getTeamId());
    }

    @Override
    public VoteResponse createVote(Long matchId, VoteRequest data) {
        User user = userService.getUserByAccessToken().orElseThrow(InvalidTokenException::new);
        Team team = teamRepository.getTeamById(data.getTeamId()).orElseThrow(NoSuchElementException::new);

        matchRepository.getMatchById(matchId).map(match -> voteRepository.save(new Vote(match, team, user)));

        return this.getVoteCounts(matchId);
    }

    private LikeResponse getLikeResponse(Match match) {
        return new LikeResponse(likeRepository.countByMatch(match));
    }

    @Override
    public LikeResponse getLikeCounts(Long matchId) {
        Match match = matchRepository.getMatchById(matchId).orElseThrow(NoSuchElementException::new);
        return getLikeResponse(match);
    }


    @Override
    public LikeResponse createLike(Long matchId) {
        User user = userService.getUserByAccessToken().orElseThrow(InvalidTokenException::new);
        Match match = matchRepository.getMatchById(matchId).orElseThrow(NoSuchElementException::new);

        likeRepository.save(new Like(match, user));
        return getLikeResponse(match);
    }
}
