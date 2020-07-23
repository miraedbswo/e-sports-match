package com.dsm.umjunsik.model.request;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class CheckUserDuplicatedRequest {
    @NotEmpty
    private String id;
}
