package com.dsm.umjunsik.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class MatchResponseList {
    /*
    {
	    "schedules": [
	        {
			    "matchId": 1,
			    "redTeamId": 2,
                "redTeamName": "Rox tigers",
                "redTeamLogoURL": "https://~",
                "RedTeamRatio": 18.9,
                "blueTeamId": 1,
                "blueTeamName": "skt t1",
                "blueTeamLogoURL": "https://~",
                "BlueTeamRatio": 81.1,
	    	    "datetime": "2020-12-16 17:00"
		    },
		    {
			    "matchId": 1,
			    "redTeamId": 2,
                "redTeamName": "Rox tigers",
                "redTeamLogoURL": "https://~",
                "RedTeamRatio": 18.9,
                "blueTeamId": 1,
                "blueTeamName": "skt t1",
                "blueTeamLogoURL": "https://~",
                "BlueTeamRatio": 81.1,
	    	    "datetime": "2020-12-16 17:00"
		    }
        ]
    }
    */

    private List<MatchResponse> schedules;
}
