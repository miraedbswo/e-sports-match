package com.dsm.umjunsik.model.request;

import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.NotEmpty;

@Getter
public class LoginRequest {
    @NotEmpty
    private String id;
    @NotEmpty
    private String password;

    public UsernamePasswordAuthenticationToken getAuthenticationToken() {
        return new UsernamePasswordAuthenticationToken(id, password);
    }
}
