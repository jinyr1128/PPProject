package com.team.gameblog.repository;

import com.team.gameblog.entity.RefreshToken;
import com.team.gameblog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {

    Optional<RefreshToken> findByRefresh(String refreshToken);

}
