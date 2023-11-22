package com.team.gameblog.controller;

import com.team.gameblog.dto.article.ArticleResponseDto;
import com.team.gameblog.entity.Article;
import com.team.gameblog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public List<ArticleResponseDto> getAllArticles() {
        return articleService.getAllArticles().stream()
                .map(article -> new ArticleResponseDto(article))
                .collect(Collectors.toList());
    }
}
