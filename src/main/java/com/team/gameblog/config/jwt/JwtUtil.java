package com.team.gameblog.config.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j(topic = "JwtUtil")
@Component
public class JwtUtil {

    public static final String Access_Header = "Access";

    public static final String Refresh_Header = "Refresh";

    public static final String BEARER_PREFIX = "Bearer ";

    // 엑세스 만료 시간 30분 설정
    private final long TOKEN_TIME = 30 * 60 * 1000L;

    // 리프레시 만료 시간 1일 설정
    private final  long REFRESH_TOKEN_TIME = 60 * 60 * 24 * 1000L;

    @Value("${jwt.secret.key}") // application.properties 에서 설정한 시크릿키 가져오기
    private String secretKey;

    private Key key;

    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init(){
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key= Keys.hmacShaKeyFor(bytes);

    }

    //한 메소드에 엑세스,리프레시 동시에 생성하면 엑세스 만료시 엑세스만 생성하는경우 쓸모없는 리프레시도 동시에 생성하니 엑세스,리프레시 메소드 생성 분리
    //이렇게 분리하면 토큰 정보에 발급일이나 시간 정보가 엑세스랑 리프레시랑 0.xxxxxxx초 차이 있을듯하나 일반 사용자는 못느끼니 지금은 패스~

    // 엑세스 토큰 생성
    public String createAccessToken(String username){
        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(username) //builder() 하고 마지막에 compact() 하면 JWT토큰 생성
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME))
                        .setIssuedAt(date) // 발급일
                        .signWith(key, signatureAlgorithm)  // 첫번째 매개변수는 시크릿키,두번째는 암호화 알고리즘
                        .compact();
    }

    // 리프레시 토큰 생성
    public String createRefreshToken(String username){
        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(username)
                        .setExpiration(new Date(date.getTime() + REFRESH_TOKEN_TIME))
                        .setIssuedAt(date)
                        .signWith(key, signatureAlgorithm)
                        .compact();
    }


    // header 에서 JWT 가져오기
    public String getJWtAccessHeader(HttpServletRequest request) {

        String bearerToken = request.getHeader(Access_Header);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            throw new JwtException("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            throw new JwtException("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            throw new JwtException("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            throw new JwtException("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }

    }

    // 토큰에서 사용자 정보 가져오기
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody(); // body안에 claim기반 데이터 반환
    }


}
