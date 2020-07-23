package com.dsm.umjunsik.service;

import com.dsm.umjunsik.exception.InvalidTokenException;
import com.dsm.umjunsik.exception.NoSuchElementException;
import com.dsm.umjunsik.model.entity.Follow;
import com.dsm.umjunsik.model.entity.Team;
import com.dsm.umjunsik.model.entity.User;
import com.dsm.umjunsik.model.request.FollowRequest;
import com.dsm.umjunsik.model.response.TeamResponseList;
import com.dsm.umjunsik.model.response.TeamResponse;
import com.dsm.umjunsik.repository.FollowRepository;
import com.dsm.umjunsik.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final UserService userService;
    private final TeamRepository teamRepository;
    private final FollowRepository followRepository;

    @Override
    public TeamResponseList getTeamList() {
        User user = userService.getUserByAccessToken().orElseThrow(InvalidTokenException::new);

        List<TeamResponse> teams = teamRepository.findAllByOrderByIdAsc().stream()
                .map(team -> new TeamResponse(team, user)
        ).collect(Collectors.toList());

        return new TeamResponseList(teams);
    }

    private void createFollow(Follow follow) {
        followRepository.save(follow);
    }

    @Override
    public TeamResponseList followTeam(FollowRequest data) {
        User user = userService.getUserByAccessToken().orElseThrow(InvalidTokenException::new);
        Team teamToFollow = teamRepository.getTeamById(data.getTeamId()).orElseThrow(NoSuchElementException::new);

        this.createFollow(
                Follow.builder()
                        .team(teamToFollow)
                        .user(user)
                        .build()
        );

        return this.getTeamList();
    }
}
