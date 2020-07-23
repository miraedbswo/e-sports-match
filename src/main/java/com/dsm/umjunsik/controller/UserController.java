package com.dsm.umjunsik.controller;

import com.dsm.umjunsik.model.request.ChangeNicknameRequest;
import com.dsm.umjunsik.model.request.CheckUserDuplicatedRequest;
import com.dsm.umjunsik.model.response.*;
import com.dsm.umjunsik.service.HighlightService;
import com.dsm.umjunsik.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final HighlightService highlightService;
    private final UserService userService;

    @PostMapping("/duplicate")
    public CheckUserDuplicatedResponse duplicate(@RequestBody @Valid CheckUserDuplicatedRequest data) {
        return userService.isDuplicated(data);
    }

    @GetMapping("/nickname")
    public GetNicknameResponse getNickname() {
        return userService.getNickname();
    }

    @PatchMapping("/nickname")
    public ChangeNicknameResponse changeNickname(@RequestBody @Valid ChangeNicknameRequest data) {
        return userService.changeNickname(data);
    }

    @PostMapping("/highlights")
    public HighlightResponseList getHighlights(@RequestParam String query) {
        return highlightService.getHighlights(query);
    }
}
