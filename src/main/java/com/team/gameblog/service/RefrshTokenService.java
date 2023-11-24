package com.team.gameblog.service;

import com.team.gameblog.config.jwt.JwtUtil;
import com.team.gameblog.exception.CustomException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class RefrshTokenService {

    private final JwtUtil jwtUtil;

    public String reissuanceAccessToken(String refreshToken) throws CustomException {

        if(refreshToken == null || !refreshToken.startsWith(JwtUtil.BEARER_PREFIX)){
            throw new CustomException(HttpStatus.BAD_REQUEST,"Refrsh토큰이 없습니다");
        }
        // 리프레시 토큰 유효성 체크
        try {
            jwtUtil.validateToken(refreshToken);
        }catch (JwtException e){
            throw new CustomException(HttpStatus.BAD_REQUEST,e.getMessage());
        }

        // 리프레시 토큰 정보에서 username 가져오기
        String username = jwtUtil.getUserInfoFromToken(refreshToken).getSubject();

        return jwtUtil.createAccessToken(username);
    }
}
