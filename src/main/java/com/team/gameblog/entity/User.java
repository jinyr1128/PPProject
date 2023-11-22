package com.team.gameblog.entity;

import com.team.gameblog.dto.user.SignupRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "users")
@NoArgsConstructor
@Getter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20, unique = true)
    private String username;

    @Column(nullable = false, length = 20, unique = true)
    private String email;


    @Column(nullable = false, length = 100)
    private String password;

    @Column(length = 200)
    private String profile;


    public User(SignupRequestDto requestDto, String password) {
        this.username = requestDto.getUsername();
        this.email = requestDto.getEmail();
        this.password = password;
        this.profile = requestDto.getProfile();

    }
}
