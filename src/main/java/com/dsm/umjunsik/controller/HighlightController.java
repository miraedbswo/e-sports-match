package com.dsm.umjunsik.controller;

import com.dsm.umjunsik.model.response.LikeResponse;
import com.dsm.umjunsik.model.response.MatchResponseList;
import com.dsm.umjunsik.service.HighlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/video")
@RequiredArgsConstructor
public class HighlightController {
    private final HighlightService highlightService;

    @GetMapping("/like/{videoId}")
    public LikeResponse getLikeCounts(@PathVariable Long videoId) {
        return highlightService.getLikeCounts(videoId);
    }

    @PostMapping("/like/{videoId}")
    public LikeResponse createLike(@PathVariable Long videoId) {
        return highlightService.createLike(videoId);
    }

}
