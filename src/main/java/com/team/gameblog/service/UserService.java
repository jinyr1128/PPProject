package com.team.gameblog.service;

import com.team.gameblog.dto.user.PasswordChangeRequestDto;
import com.team.gameblog.dto.user.ProfileRequestDto;
import com.team.gameblog.dto.user.ProfileResponseDto;
import com.team.gameblog.dto.user.SignupRequestDto;
import com.team.gameblog.entity.User;
import com.team.gameblog.exception.CustomException;
import com.team.gameblog.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signup(SignupRequestDto requestDto) {

        String password = passwordEncoder.encode(requestDto.getPassword());

        if (!(requestDto.getPassword().equals(requestDto.getPasswordCheck()))) {
            throw new IllegalArgumentException("비밀번호 확인이 다릅니다.");
        }

        Optional<User> checkUsername = userRepository.findByUsername(requestDto.getUsername());
        Optional<User> checkemail = userRepository.findByUsername(requestDto.getEmail());

        if (checkUsername.isPresent() && checkemail.isPresent()) {
            throw new IllegalArgumentException("username,email 둘다 중복입니다");
        } else if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 username 입니다.");
        } else if (checkemail.isPresent()) {
            throw new IllegalArgumentException("중복된 email 입니다.");
        }

        User user = new User(requestDto, password);

        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public ProfileResponseDto getProfileUpdatePage(User user) {

        return new ProfileResponseDto(user);
    }


    @Transactional
    public void updateProfile(ProfileRequestDto requestDto, User user) {

        user.profileUpdate(requestDto);
    }

    @Transactional
    public void updatePassword(PasswordChangeRequestDto requestDto, User user) throws CustomException {
        String oldPassword = passwordEncoder.encode(requestDto.getOldPassword());
        String password = passwordEncoder.encode(requestDto.getNewPassword());

        //로그인중 유저 패스워드랑 request에 담긴 예전 패스워드랑 같은지 체크
        if (!(user.getPassword().equals(oldPassword))) {
            throw new CustomException(HttpStatus.FORBIDDEN, "현재 비밀번호가 일치하지 않습니다");
        }
        //변경할 비번,변경할비번 확인 같은지 체크
        else if (!(requestDto.getNewPassword().equals(requestDto.getPasswordCheck()))) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "변경할 비밀번호랑 비밀번호 확인이랑 다릅니다.");
        }

        user.passwordUpdate(password);

    }
}

