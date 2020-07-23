package com.dsm.umjunsik.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeamResponseList {
    /*
    {
	    "following": [
	        {
	            "teamId": 2,
                "teamName": "Rox tigers",
	            "isFollowing": true
            },
	        {
	            "teamId": 1,
                "teamName": "skt t1",
	            "isFollowing": false
	        }
        ]
    }
    */

    private List<TeamResponse> following;
}
