package com.dsm.umjunsik.controller;

import com.dsm.umjunsik.model.request.FollowRequest;
import com.dsm.umjunsik.model.response.TeamResponseList;
import com.dsm.umjunsik.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/team")
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;

    @GetMapping("")
    public TeamResponseList getTeamList() {
        return teamService.getTeamList();
    }

    @PostMapping("/follow")
    public TeamResponseList followTeam(@RequestBody @Valid FollowRequest data) {
        return teamService.followTeam(data);
    }
}
