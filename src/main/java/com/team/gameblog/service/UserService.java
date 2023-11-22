package com.team.gameblog.service;

import com.team.gameblog.dto.user.ProfileRequestDto;
import com.team.gameblog.dto.user.SignupRequestDto;
import com.team.gameblog.entity.User;
import com.team.gameblog.exception.CustomException;
import com.team.gameblog.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

}


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(SignupRequestDto requestDto) {

        String password = passwordEncoder.encode(requestDto.getPassword());

        if( !(requestDto.getPassword().equals(requestDto.getPasswordCheck())) ){
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

        User user = new User(requestDto,password);

        userRepository.save(user);
    }


    public void updateProfile(Long id, @Valid ProfileRequestDto requestDto, User user) throws CustomException {

        User findUser = userRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("해당 유저는 없습니다")
        );

        if(!findUser.getUsername().equals(user.getUsername())) {
            throw new CustomException(HttpStatus.BAD_REQUEST,"해당 프로필은 본인 프로필 아닙니다.");

        }


    }
}

