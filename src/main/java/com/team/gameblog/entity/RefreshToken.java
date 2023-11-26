package com.team.gameblog.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "refreshtoken")
@NoArgsConstructor
@Entity
@Getter
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,  unique = true)
    private String refresh;

    public RefreshToken(String tokenValue) {
        this.refresh = tokenValue;
    }
}
