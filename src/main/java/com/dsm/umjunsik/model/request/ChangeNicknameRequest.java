package com.dsm.umjunsik.model.request;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class ChangeNicknameRequest {
    @NotEmpty
    private String currentNickname;
    @NotEmpty
    private String newNickname;
}
