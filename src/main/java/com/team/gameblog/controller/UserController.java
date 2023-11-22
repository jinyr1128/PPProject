package com.team.gameblog.controller;

import com.team.gameblog.dto.user.SignupRequestDto;
import com.team.gameblog.entity.User;
import com.team.gameblog.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    //회원가입
    @PostMapping("/user/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid SignupRequestDto requestDto, BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getFieldErrors());
        }

        userService.signup(requestDto);
        return ResponseEntity.ok("가입완료");

    }

    //회원가입 페이지
    @GetMapping("/user/signup")
    public String signupPage() {

        return "";
    }

    // 로그인 페이지
    @GetMapping("/user/login-page")
    public String loginPage() {
        return "";
    }

    // 로그아웃후 전체 게시글 페이지
    @GetMapping("/logout")
    public String logout(){

        return "";
    }

    //프로필 생성
//    @PostMapping("/profile")
//    public PorofileResponseDto createPrfile(){
//        return userService.createProfile();
//    }
}
