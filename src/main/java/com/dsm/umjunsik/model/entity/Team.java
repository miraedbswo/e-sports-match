package com.dsm.umjunsik.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "team")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String image;

    @ManyToMany
    @OrderColumn
    private List<Highlight> highlights;
}
