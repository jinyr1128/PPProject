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
import org.springframework.ui.Model;
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
    public String signupPage(Model model) {
        model.addAttribute("signupRequestDto", new SignupRequestDto());
        System.out.println("회원가입 페이지 시작");
        return "login";
    }

    // 로그인 페이지
    @GetMapping("/user/login-page")
    public String loginPage() {
        System.out.println("로그인 페이지 시작");
        return "login";
    }


    // 로그아웃
    @ResponseBody
    @GetMapping("/logout/page")
    public ResponseEntity<String> logout(@RequestHeader("Refresh") String refreshToken,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails) throws CustomException {
        System.out.println("로그아웃 컨트롤 시작");
        userService.logout(refreshToken, userDetails.getUser());

        return ResponseEntity.ok("로그아웃 완료");
    }


    // 프로필 수정 페이지
    @ResponseBody
    @GetMapping("/profile/page")
    public ResponseEntity<ProfileResponseDto> getProfileUpdatePage(@AuthenticationPrincipal UserDetailsImpl userDetails) {

        ProfileResponseDto profileResponseDto = userService.getProfileUpdatePage(userDetails.getUser());

        return ResponseEntity.ok(profileResponseDto);
    }


    // 프로필 수정
    // put,patch는 프론트랑 협의후 결정 해야하는데 현재 프론트에서 한번에 변경 모든 필드를 보내도록 해서 put
    @ResponseBody
    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody @Valid ProfileRequestDto requestDto,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails,
                                           BindingResult bindingResult) {
        // 이미 페이지에 변경전 정보들 폼에 입력된 상태로 가지만 유저가 실수로 다 지우고 보낼수도 있어서
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getFieldErrors());
        }

        ProfileResponseDto profileResponseDto = userService.updateProfile(requestDto, userDetails.getUser());

        return ResponseEntity.ok(profileResponseDto);
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

