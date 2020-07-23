package com.dsm.umjunsik.model.request;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class FollowRequest {
    @NotEmpty
    private Long teamId;
}
