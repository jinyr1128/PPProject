package com.team.gameblog.service;

import com.team.gameblog.config.jwt.JwtUtil;
import com.team.gameblog.entity.RefreshToken;
import com.team.gameblog.exception.CustomException;
import com.team.gameblog.repository.RefreshTokenRepository;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Optional;


@RequiredArgsConstructor
@Service
public class RefrshTokenService {

    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional(readOnly = true)
    public String reissuanceAccessToken(String refreshToken) throws CustomException {

        if (!(StringUtils.hasText(refreshToken)) || !refreshToken.startsWith(JwtUtil.BEARER_PREFIX)) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Refrsh토큰이 없습니다");
        }

        //리프레시 토큰값에 "Bearer "제거
        String tokenValue = refreshToken.substring(7);

        // 리프레시 토큰 유효성 체크
        try {
            jwtUtil.validateToken(tokenValue);
        } catch (JwtException e) {
            throw new CustomException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        // DB에 클라한테 받은 리프레시 토큰이 이미 존재하는지
        Optional<RefreshToken> refresh = refreshTokenRepository.findByRefresh(tokenValue);

        // 이미 로그아웃한 상태인지
        if (refresh.isPresent()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "다시 로그인 해주십쇼");
        }

        // 리프레시 토큰 정보에서 username 가져오기
        String username = jwtUtil.getUserInfoFromToken(tokenValue).getSubject();

        return jwtUtil.createAccessToken(username);
    }

}
