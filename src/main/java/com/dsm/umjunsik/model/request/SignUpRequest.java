package com.dsm.umjunsik.model.request;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class SignUpRequest {
    @NotEmpty
    private String id;

    @NotEmpty
    private String password;

    @NotEmpty
    private String nickname;
}
