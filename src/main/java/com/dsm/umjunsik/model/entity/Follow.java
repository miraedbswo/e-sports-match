package com.dsm.umjunsik.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "follow")
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "user_idx", referencedColumnName = "idx")
    private User user;

}
