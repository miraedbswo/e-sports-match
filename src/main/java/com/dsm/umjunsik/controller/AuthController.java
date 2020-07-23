package com.dsm.umjunsik.controller;

import com.dsm.umjunsik.model.request.LoginRequest;
import com.dsm.umjunsik.model.request.SignUpRequest;
import com.dsm.umjunsik.model.response.UserTokenResponse;
import com.dsm.umjunsik.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public UserTokenResponse signUp(@RequestBody @Valid SignUpRequest data) {
        return authService.signUp(data);
    }

    @PostMapping("/login")
    public UserTokenResponse login(@RequestBody @Valid LoginRequest data) {
        return authService.login(data);
    }


}
