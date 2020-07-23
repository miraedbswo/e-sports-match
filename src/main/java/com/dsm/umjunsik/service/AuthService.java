package com.dsm.umjunsik.service;

import com.dsm.umjunsik.model.request.CheckUserDuplicatedRequest;
import com.dsm.umjunsik.model.request.LoginRequest;
import com.dsm.umjunsik.model.request.SignUpRequest;
import com.dsm.umjunsik.model.response.CheckUserDuplicatedResponse;
import com.dsm.umjunsik.model.response.UserTokenResponse;

public interface AuthService {
    UserTokenResponse signUp(SignUpRequest data);
    UserTokenResponse login(LoginRequest data);
}
