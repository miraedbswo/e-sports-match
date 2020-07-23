package com.dsm.umjunsik.service;

import com.dsm.umjunsik.model.request.FollowRequest;
import com.dsm.umjunsik.model.response.TeamResponseList;

public interface TeamService {
    TeamResponseList getTeamList();
    TeamResponseList followTeam(FollowRequest data);
}
