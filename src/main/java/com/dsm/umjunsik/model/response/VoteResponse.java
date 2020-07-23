package com.dsm.umjunsik.model.response;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoteResponse {
    private Integer redTeamVoteCount;
    private Integer blueTeamVoteCount;
}
