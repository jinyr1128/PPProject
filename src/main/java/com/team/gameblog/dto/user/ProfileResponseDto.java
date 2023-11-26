package com.team.gameblog.dto.user;

import com.team.gameblog.entity.User;
import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class ProfileResponseDto {

    private Long id;

    private String email;

    private String username;

    private String introduction;

    public ProfileResponseDto(User dbUser) {
        this.id = dbUser.getId();
        this.email = dbUser.getEmail();
        this.username = dbUser.getUsername();
        this.introduction = dbUser.getIntroduction();
    }
}
