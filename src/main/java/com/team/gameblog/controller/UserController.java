package com.team.gameblog.controller;

import com.team.gameblog.dto.user.ProfileRequestDto;
import com.team.gameblog.dto.user.SignupRequestDto;
import com.team.gameblog.entity.User;
import com.team.gameblog.security.UserDetailsImpl;
import com.team.gameblog.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    //회원가입
    @ResponseBody
    @PostMapping("/user/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid SignupRequestDto requestDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
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
    public String logout() {

        return "";
    }

    @ResponseBody
    @PutMapping("/user/profile/{id}")
    public ResponseEntity<?> updateProfile(@PathVariable Long id,
                                              @RequestBody @Valid ProfileRequestDto requestDto,
                                              @AuthenticationPrincipal UserDetailsImpl userDetails,
                                              BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getFieldErrors());
        }

        userService.updateProfile(id,requestDto,userDetails.getUser());
        return ResponseEntity.ok("프로필 수정 완료");

    }

}

