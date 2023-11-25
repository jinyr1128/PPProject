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


    // 로그아웃
    @GetMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Refresh") String refreshToken,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails) throws CustomException {

        userService.logout(refreshToken,userDetails.getUser());

        return ResponseEntity.ok("로그아웃 완료");
    }


    // 프로필 수정 페이지
    @GetMapping("/profile")
    public ResponseEntity<ProfileResponseDto> getProfileUpdatePage(@AuthenticationPrincipal UserDetailsImpl userDetails) {

        ProfileResponseDto profileResponseDto = userService.getProfileUpdatePage(userDetails.getUser());
        return ResponseEntity.ok(profileResponseDto);
    }


    // 프로필 수정
    // 프로필 수정에서 어떤 필드는 변경하지 않아도 모든 필드 정보 보내는 경우로(만약 프론트가 변경하는 필드 정보만 보내면 patch로 변경)
    @ResponseBody
    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody @Valid ProfileRequestDto requestDto,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails,
                                           BindingResult bindingResult) {
        // 이미 페이지에 변경전 정보들 폼에 입력된 상태로 가지만 유저가 실수로 다 지우고 보낼수도 있어서
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

