package com.dsm.umjunsik.service;

import com.dsm.umjunsik.exception.InvalidTokenException;
import com.dsm.umjunsik.exception.NoSuchElementException;
import com.dsm.umjunsik.model.entity.*;
import com.dsm.umjunsik.model.response.HighlightResponse;
import com.dsm.umjunsik.model.response.HighlightResponseList;
import com.dsm.umjunsik.model.response.LikeResponse;
import com.dsm.umjunsik.repository.HighlightRepository;
import com.dsm.umjunsik.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HighlightServiceImpl implements HighlightService {

    private final UserService userService;
    private final LikeRepository likeRepository;
    private final HighlightRepository highlightRepository;

    @Override
    public HighlightResponseList getHighlights(String query) {
        if (query.isBlank()) {
            return new HighlightResponseList(
                    searchHighlights(query)
            );
        }
        return new HighlightResponseList(
                getFollowedTeamsVideo()
        );
    }

    @Override
    public LikeResponse getLikeCounts(Long videoId) {
        Highlight highlight = highlightRepository.getHighlightById(videoId).orElseThrow(NoSuchElementException::new);
        return getLikeResponse(highlight);
    }

    @Override
    public LikeResponse createLike(Long videoId) {
        User user = userService.getUserByAccessToken().orElseThrow(InvalidTokenException::new);
        Highlight highlight = highlightRepository.getHighlightById(videoId).orElseThrow(NoSuchElementException::new);

        likeRepository.save(new Like(highlight, user));
        return getLikeResponse(highlight);
    }


    private LikeResponse getLikeResponse(Highlight highlight) {
        return new LikeResponse(likeRepository.countByHighlight(highlight));
    }

    private List<HighlightResponse> searchHighlights(String query) {
        return highlightRepository.findByVideoNameContainingOrderByDatetimeDesc(query).stream()
                .map(highlight -> new HighlightResponse(
                        highlight,
                        likeRepository.countByHighlight(highlight)
                )).collect(Collectors.toList());
    }

    private List<HighlightResponse> getFollowedTeamsVideo() {
        User user = userService.getUserByAccessToken().orElseThrow(InvalidTokenException::new);

        List<Team> followedTeams = user.getFollows().stream()
                .map(Follow::getTeam).collect(Collectors.toList());

        return highlightRepository.findDistinctByTeamsInOrderByDatetimeDesc(followedTeams).stream()
                .map(highlight -> new HighlightResponse(
                        highlight,
                        likeRepository.countByHighlight(highlight)
        )).collect(Collectors.toList());
    }
}
