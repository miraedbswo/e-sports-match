package com.dsm.umjunsik.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HighlightResponseList {
    /*
    {
	    "videos": [
		    {
			    "videoId": 1,
		        "videoName": "행복했꿀벌 | DRX vs APK H/L 04.16 |",
			    "videoImageURL": "https://svn/drx.png",
			    "videoURL": "https://youtube.com/watch?v=_value",
			    "datetime": "2019-12-16",
			    "likeCount": 4000000
		    }
        ]
    }
     */

    private List<HighlightResponse> videos;
}
