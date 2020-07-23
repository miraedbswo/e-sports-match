package com.dsm.umjunsik.model.response;

import com.dsm.umjunsik.model.entity.Highlight;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class HighlightResponse {
    private Long videoId;
    private String videoName;
    private String videoImageURL;
    private String videoURL;
    private LocalDateTime datetime;
    private Integer likeCount;

    public HighlightResponse(Highlight highlight, Integer likeCount) {
        this.videoId = highlight.getId();
        this.videoName = highlight.getVideoName();
        this.videoImageURL = highlight.getVideoImageURL();
        this.videoURL = highlight.getVideoURL();
        this.datetime = highlight.getDatetime();
        this.likeCount = likeCount;
    }
}
