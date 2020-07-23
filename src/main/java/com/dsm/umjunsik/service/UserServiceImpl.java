package com.dsm.umjunsik.service;

import com.dsm.umjunsik.model.entity.User;
import com.dsm.umjunsik.model.request.ChangeNicknameRequest;
import com.dsm.umjunsik.model.request.CheckUserDuplicatedRequest;
import com.dsm.umjunsik.model.response.ChangeNicknameResponse;
import com.dsm.umjunsik.model.response.CheckUserDuplicatedResponse;
import com.dsm.umjunsik.model.response.GetNicknameResponse;
import com.dsm.umjunsik.repository.UserRepository;
import com.dsm.umjunsik.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthenticationFacade authenticationFacade;

    @Override
    public Optional<User> getUserByAccessToken() {
        return findUserById(authenticationFacade.getUsername());
    }

    @Override
    public Optional<User> findUserById(String id) {
        return userRepository.findUserById(id);
    }

    @Override
    public CheckUserDuplicatedResponse isDuplicated(CheckUserDuplicatedRequest data) {
        Optional<User> user = findUserById(data.getId());
        return new CheckUserDuplicatedResponse(user.isPresent());
    }

    @Override
    public User save(User user) {
        userRepository.save(user);
        return user;
    }

    @Override
    public ChangeNicknameResponse changeNickname(ChangeNicknameRequest data) {
        return getUserByAccessToken()
                .map(user -> {
                    user.setNickname(data.getNewNickname());
                    return new ChangeNicknameResponse(user.getNickname());
                }).get();
    }

    @Override
    public GetNicknameResponse getNickname() {
        return getUserByAccessToken()
                .map(user -> new GetNicknameResponse(user.getNickname())).get();
    }
}
