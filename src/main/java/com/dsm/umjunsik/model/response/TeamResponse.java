package com.dsm.umjunsik.model.response;

import com.dsm.umjunsik.model.entity.Follow;
import com.dsm.umjunsik.model.entity.Team;
import com.dsm.umjunsik.model.entity.User;
import lombok.*;

import java.util.stream.Stream;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamResponse {
    private Long teamId;
    private String teamName;
    private String teamImage;
    private Boolean isFollowing;

    public TeamResponse(Team team, User user) {
        this.teamId = team.getId();
        this.teamImage = team.getImage();
        this.teamName = team.getName();
        this.isFollowing = Stream.of(user.getFollows().stream()
                .map(Follow::getId).toArray()).anyMatch(x -> x == team.getId());
    }
}
