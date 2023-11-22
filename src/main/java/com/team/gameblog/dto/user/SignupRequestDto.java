package com.team.gameblog.dto.user;

import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class SignupRequestDto {


    @NotBlank
    @Email(message = "이메일 형식에 맞게 적어주세요")
    private String email;


    @Pattern(regexp ="[a-zA-Z0-9]*$",message ="비밀번호 허용 문자에 맞게 해주세요")
    @Size(min=8, max=15,message ="비밀번호 8자 이상 15자 이하이어야 합니다")
    @NotBlank
    private String password;

    @NotBlank
    private String passwordCheck;


    @Pattern(regexp ="^[a-z]+[0-9]*$",message ="아이디 허용문자에 맞게 적어주세요")
    @Size(min=4, max=10,message ="아이디 4자 이상 10자 이하이어야 합니다")
    @NotBlank
    private String username;

    @Max(value = 300,message = "최대 300자 까지 입니다.")
    private String profile;

}
