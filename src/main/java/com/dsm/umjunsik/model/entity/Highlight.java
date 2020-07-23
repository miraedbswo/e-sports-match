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
@Table(name = "highlight")
public class Highlight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "video_name")
    private String videoName;

    @Column(name = "video_url")
    private String videoURL;

    @Column(name = "video_image_url")
    private String videoImageURL;

    @Column
    private LocalDateTime datetime;

    @ManyToMany
    @OrderColumn
    private List<Team> teams;
}
