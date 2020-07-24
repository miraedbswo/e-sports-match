package com.dsm.umjunsik.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Entity
@Builder
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column
    private String id;

    @Column
    private String password;

    @Column(unique = true)
    private String nickname;

    private String roles;

    @OneToMany
    private List<Follow> follows = new ArrayList<>();

    public static User anonymous(String id, String encodedPassword, String nickname) {
        return User.builder()
                .id(id)
                .password(encodedPassword)
                .nickname(nickname)
                .roles("ROLE_USER")
                .build();
    }

    public List<String> getRoles() {
        return Arrays.asList(this.roles.split(","));
    }
}