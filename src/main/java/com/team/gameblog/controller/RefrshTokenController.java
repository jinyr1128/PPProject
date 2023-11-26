package com.team.gameblog.controller;

import com.team.gameblog.config.jwt.JwtUtil;
import com.team.gameblog.exception.CustomException;
import com.team.gameblog.service.RefreshTokenService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class RefrshTokenController {

    private final RefreshTokenService refrshTokenService;

    @GetMapping("/user/refresh-token")
    public ResponseEntity<String> reissuanceAccessToken(
            @RequestHeader("Refresh") String refreshToken,
            HttpServletResponse response) throws CustomException {

        String AccessToken = refrshTokenService.reissuanceAccessToken(refreshToken);

        // 헤더에 직접 리프레시,재발급 받은 엑세스 토큰 넣기
        response.addHeader(JwtUtil.Access_Header, AccessToken);
        response.addHeader(JwtUtil.Refresh_Header, refreshToken);

        return ResponseEntity.ok("AccessToken 재발급 완료");
    }
}
