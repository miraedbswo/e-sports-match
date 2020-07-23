package com.dsm.umjunsik.service;

import com.dsm.umjunsik.model.response.HighlightResponseList;
import com.dsm.umjunsik.model.response.LikeResponse;

public interface HighlightService {
    HighlightResponseList getHighlights(String query);

    LikeResponse getLikeCounts(Long videoId);

    LikeResponse createLike(Long videoId);
}
