package com.team.gameblog.entity;

import com.team.gameblog.dto.user.ProfileRequestDto;
import com.team.gameblog.dto.user.SignupRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    @Column(nullable = false, length = 300)
    private String introduction;

    @OneToMany(mappedBy = "user")
    private List<Article> articleList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Comment> commentList = new ArrayList<>();


    public User(SignupRequestDto requestDto, String password) {
        this.username = requestDto.getUsername();
        this.email = requestDto.getEmail();
        this.password = password;
        this.introduction = requestDto.getProfile();
    }

    public void profileUpdate(ProfileRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.email = requestDto.getEmail();
        this.introduction = requestDto.getIntroduction();
    }

    public void passwordUpdate(String password) {
        this.password = password;
    }
}
