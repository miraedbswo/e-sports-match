package com.dsm.umjunsik.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoteStatistic {
    private Long teamId;
    private Integer teamVoteCount;
}
