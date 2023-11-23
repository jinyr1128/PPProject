package com.team.gameblog.dto.user;

import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class ProfileRequestDto {

    @Email(message = "이메일 형식에 맞게 적어주세요")
    @NotBlank
    private String email;

    @Pattern(regexp ="^[a-z]+[0-9]*$",message ="닉네임 허용문자에 맞게 적어주세요")
    @Size(min=4, max=10,message ="닉네임 4자 이상 10자 이하이어야 합니다")
    @NotBlank
    private String username;


    @Max(value = 300,message = "최대 300자 까지 입니다.")
    private String introduction;


}
