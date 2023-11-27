package com.team.gameblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class GameBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(GameBlogApplication.class, args);
    }

}
