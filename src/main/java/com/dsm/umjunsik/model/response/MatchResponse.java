package com.dsm.umjunsik.model.response;

import com.dsm.umjunsik.model.entity.Match;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchResponse {
    private Long matchId;
    private Long redTeamId;
    private String redTeamName;
    private String redTeamLogoURL;
    private Integer redTeamVoteCount;
    private Long blueTeamId;
    private String blueTeamName;
    private String blueTeamLogoURL;
    private Integer blueTeamVoteCount;
    private LocalDateTime datetime;

    public MatchResponse(Match match, Integer redTeamVoteCount, Integer blueTeamVoteCount) {
        this.matchId = match.getId();
        this.redTeamId = match.getRedTeam().getId();
        this.redTeamName = match.getRedTeam().getName();
        this.redTeamLogoURL = match.getRedTeam().getImage();
        this.redTeamVoteCount = redTeamVoteCount;
        this.blueTeamId = match.getBlueTeam().getId();
        this.blueTeamName = match.getBlueTeam().getName();
        this.blueTeamLogoURL = match.getBlueTeam().getImage();
        this.blueTeamVoteCount = blueTeamVoteCount;
        this.datetime = match.getDatetime();
    }

}
