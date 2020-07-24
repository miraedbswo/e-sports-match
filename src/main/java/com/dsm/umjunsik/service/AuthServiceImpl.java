package com.dsm.umjunsik.service;

import com.dsm.umjunsik.exception.AlreadyExistException;
import com.dsm.umjunsik.model.entity.User;
import com.dsm.umjunsik.model.request.LoginRequest;
import com.dsm.umjunsik.model.request.SignUpRequest;
import com.dsm.umjunsik.model.response.UserTokenResponse;
import com.dsm.umjunsik.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @Override
    public UserTokenResponse login(LoginRequest data) {
        return userService.findUserById(data.getId())
                .map(user -> {
                    authenticate(data.getAuthenticationToken());
                    return new UserTokenResponse(jwtTokenProvider.createAccessToken(user.getId()));
                })
                .orElseThrow(() -> new UsernameNotFoundException("Username not found."));
    }

    @Override
    public UserTokenResponse signUp(SignUpRequest data) {
        userService.findUserById(data.getId())
                .ifPresent(user -> {
                    throw new AlreadyExistException("Account Already Exist");
                });

        return Optional.of(User.anonymous(data.getId(), passwordEncoder.encode(data.getPassword()), data.getNickname()))
                .map(user -> {
                    userService.save(user);
                    String accessToken = jwtTokenProvider.createAccessToken(user.getId());
                    return new UserTokenResponse(accessToken);
                }).get();
    }

    private void authenticate(UsernamePasswordAuthenticationToken authToken) {
        try {
            authenticationManager.authenticate(authToken);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException(e.getMessage());
        }
    }

}
