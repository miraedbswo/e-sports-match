package com.dsm.umjunsik.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "matches")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String liveURL;

    @Column
    private String score;

    @Column
    private LocalDateTime datetime;

    @ManyToOne
    @JoinColumn(name = "red_team_id")
    private Team redTeam;

    @ManyToOne
    @JoinColumn(name = "blue_team_id")
    private Team blueTeam;

    @OneToMany
    private List<Vote> votes = new ArrayList<>();

    @OneToMany
    private List<Like> likes = new ArrayList<>();
}
