package com.team.gameblog.service;

import com.team.gameblog.config.jwt.JwtUtil;
import com.team.gameblog.dto.user.PasswordChangeRequestDto;
import com.team.gameblog.dto.user.ProfileRequestDto;
import com.team.gameblog.dto.user.ProfileResponseDto;
import com.team.gameblog.dto.user.SignupRequestDto;
import com.team.gameblog.entity.RefreshToken;
import com.team.gameblog.entity.User;
import com.team.gameblog.exception.CustomException;
import com.team.gameblog.repository.RefreshTokenRepository;
import com.team.gameblog.repository.UserRepository;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public void signup(SignupRequestDto requestDto) {

        String password = passwordEncoder.encode(requestDto.getPassword());

        if (!(requestDto.getPassword().equals(requestDto.getPasswordCheck()))) {
            throw new IllegalArgumentException("비밀번호 확인이 다릅니다.");
        }

        //이름,이메일 db중복 체크
        nameCheck(requestDto.getUsername());
        emailCheck(requestDto.getEmail());

        User user = new User(requestDto, password);

        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public ProfileResponseDto getProfileUpdatePage(User user) {

        return new ProfileResponseDto(user);
    }


    @Transactional
    public ProfileResponseDto updateProfile(ProfileRequestDto requestDto, User user) {

        //변경할 필드가 무엇인지 체크 하고 변경할 필드만 DB중복 체크
        HashMap<String, String> map = requestDto.fieldChangeCheck(user);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            switch (entry.getKey()){
                case "username" -> nameCheck(entry.getValue());
                case "email" -> emailCheck(entry.getValue());
            }
        }

        user.profileUpdate(requestDto);

        //유저를 매개변수로 받아와서 더티체킹이 불가라 일부로 직접 save방식 선택
        userRepository.save(user);

        return new ProfileResponseDto(user);
    }

    @Transactional
    public void updatePassword(PasswordChangeRequestDto requestDto, User user) throws CustomException {

        String password = passwordEncoder.encode(requestDto.getNewPassword());

        //로그인중 유저 패스워드랑 request에 담긴 예전 패스워드랑 같은지 체크
        if( !(passwordEncoder.matches(requestDto.getOldPassword(),user.getPassword()))){
            throw new CustomException(HttpStatus.FORBIDDEN, "현재 비밀번호가 일치하지 않습니다");
        }

        //변경할 비번,변경할비번 확인 같은지 체크
        else if (!(requestDto.getNewPassword().equals(requestDto.getPasswordCheck()))) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "변경할 비밀번호랑 비밀번호 확인이랑 다릅니다.");
        }

        user.passwordUpdate(password);

        // updateProfile() 처럼 마찬가지로 일부로 직접 save방식 선택
        userRepository.save(user);

    }

    @Transactional
    public void logout(String refreshToken, User user) throws CustomException {

        //리프레시 토큰값에 "Bearer "제거
        String tokenValue = refreshToken.substring(7);

        // 리프레시 토큰 유효성 체크
        try {
            jwtUtil.validateToken(tokenValue);
        } catch (JwtException e) {
            throw new CustomException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        // 리프레시 토큰 정보에서 username 가져오기
        String refreshName = jwtUtil.getUserInfoFromToken(tokenValue).getSubject();

        // DB에 클라한테 받은 리프레시 토큰이 이미 존재하는지
        Optional<RefreshToken> refresh = refreshTokenRepository.findByRefresh(tokenValue);

        // 현재 로그인중 유저랑 리프레시 토큰 정보의 유저랑 같은지 체크
        if (!(refreshName.equals(user.getUsername()))) {
            throw new CustomException(HttpStatus.FORBIDDEN, "현재 사용자의 Refresh 토큰이 아닙니다.");
        }
        // DB에 클라한테 받은 리프레시 토큰이 존재하면 이미 로그아웃한 상태
        else if (refresh.isPresent()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "이미 로그아웃 했습니다.");
        }

        refreshTokenRepository.save(new RefreshToken(tokenValue));
        System.out.println("로그아웃 서비스 성공");
    }


    // 이름  DB에 이미 있는지 중복 체크 메소드
    private void nameCheck(String username) {
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 username 입니다.");
        }
    }

    // 이름,이메일 DB에 이미 있는지 중복 체크 메소드
    private void emailCheck(String email) {
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 email 입니다.");
        }
    }

}

