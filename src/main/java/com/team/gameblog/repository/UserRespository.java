package com.team.gameblog.repository;

import com.team.gameblog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRespository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
}
