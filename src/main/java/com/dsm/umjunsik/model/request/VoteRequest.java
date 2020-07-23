package com.dsm.umjunsik.model.request;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class VoteRequest {
    @NotEmpty
    private Long teamId;
}
