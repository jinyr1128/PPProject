package com.team.gameblog.controller;

import com.team.gameblog.dto.user.PasswordChangeRequestDto;
import com.team.gameblog.dto.user.ProfileRequestDto;
import com.team.gameblog.dto.user.ProfileResponseDto;
import com.team.gameblog.dto.user.SignupRequestDto;
import com.team.gameblog.exception.CustomException;
import com.team.gameblog.security.UserDetailsImpl;
import com.team.gameblog.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

// url에 user가 있으면 인가 패스하고 다 통과하고 user 없으면 다 인가 필요
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

        return "login";
    }

    // 로그인 페이지
    @GetMapping("/user/login-page")
    public String loginPage() {
        return "login";
    }

    // 로그아웃후 (로그인or회원가입) 페이지
    @GetMapping("/logout")
    public String logout() {

        return "";
    }

    // 프로필 수정 페이지
    @GetMapping("/profile")
    public ResponseEntity<ProfileResponseDto> getProfileUpdatePage(@AuthenticationPrincipal UserDetailsImpl userDetails) {

        ProfileResponseDto profileResponseDto = userService.getProfileUpdatePage(userDetails.getUser());
        return ResponseEntity.ok(profileResponseDto);
    }

    // 프로필 수정
    // patch는 지금 상태로는 복잡해서 일단 put으로
    @ResponseBody
    @PutMapping("/profile/{id}")
    public ResponseEntity<?> updateProfile(@RequestBody @Valid ProfileRequestDto requestDto,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails,
                                           BindingResult bindingResult) {

        // 이미 페이지에 변경전 정보들 폼에 입력된 상태로 가지만 유저가 실수로 지우고 보낼수도 있어서
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getFieldErrors());
        }

        userService.updateProfile(requestDto, userDetails.getUser());

        return ResponseEntity.ok("프로필 수정 완료");
    }

    //비밀번호 변경
    @ResponseBody
    @PutMapping("/profile/password")
    public ResponseEntity<?> updatePassword(@RequestBody @Valid PasswordChangeRequestDto requestDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails,
                                            BindingResult bindingResult) throws CustomException {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getFieldErrors());
        }

        userService.updatePassword(requestDto, userDetails.getUser());

        return ResponseEntity.ok("비밀번호 변경 완료");
    }


}

