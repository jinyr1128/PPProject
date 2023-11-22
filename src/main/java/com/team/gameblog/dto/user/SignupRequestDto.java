package com.team.gameblog.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SignupRequestDto {


    @NotBlank
    @Email
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


    private String profile;

}
