package com.dsm.umjunsik.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "likes")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "highlight_id", referencedColumnName = "id")
    private Highlight highlight;

    @ManyToOne
    @JoinColumn(name = "match_id", referencedColumnName = "id")
    private Match match;

    @ManyToOne
    @JoinColumn(name = "user_idx", referencedColumnName = "idx")
    private User user;

    public Like(Match match, User user) {
        this.match = match;
        this.user = user;
    }

    public Like(Highlight highlight, User user) {
        this.highlight = highlight;
        this.user = user;
    }
}
