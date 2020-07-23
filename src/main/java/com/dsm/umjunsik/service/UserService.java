package com.dsm.umjunsik.service;

import com.dsm.umjunsik.model.entity.User;
import com.dsm.umjunsik.model.request.ChangeNicknameRequest;
import com.dsm.umjunsik.model.request.CheckUserDuplicatedRequest;
import com.dsm.umjunsik.model.response.ChangeNicknameResponse;
import com.dsm.umjunsik.model.response.CheckUserDuplicatedResponse;
import com.dsm.umjunsik.model.response.GetNicknameResponse;

import java.util.Optional;

public interface UserService {
    Optional<User> getUserByAccessToken();
    Optional<User> findUserById(String id);
    CheckUserDuplicatedResponse isDuplicated(CheckUserDuplicatedRequest data);
    User save(User user);
    ChangeNicknameResponse changeNickname(ChangeNicknameRequest data);
    GetNicknameResponse getNickname();
}
