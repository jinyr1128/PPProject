package com.team.gameblog.controller;

import com.team.gameblog.dto.article.ArticleRequestDto;
import com.team.gameblog.dto.article.ArticleResponseDto;
import com.team.gameblog.entity.Article;
import com.team.gameblog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    // 모든 게시물 조회
    @GetMapping
    public ResponseEntity<List<ArticleResponseDto>> getAllArticles() {
        List<Article> articles = articleService.getAllArticles();
        List<ArticleResponseDto> articlesDto = articles.stream()
                .map(article -> new ArticleResponseDto(article))
                .collect(Collectors.toList());
        return new ResponseEntity<>(articlesDto, HttpStatus.OK);
    }

    // 게시물 ID로 조회
    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponseDto> getArticleById(@PathVariable Long id) {
        Article article = articleService.getArticleById(id);
        ArticleResponseDto articleDto = new ArticleResponseDto(article);
        return new ResponseEntity<>(articleDto, HttpStatus.OK);
    }

    // 게시물 작성
    @PostMapping
    public ResponseEntity<ArticleResponseDto> createArticle(@RequestBody ArticleRequestDto articleDto) {
        Article article = new Article();
        article.setTitle(articleDto.getTitle());
        article.setContent(articleDto.getContent());
        // TODO: 현재 로그인한 사용자 정보 설정
        // article.setUser(user);
        Article savedArticle = articleService.createArticle(article);
        ArticleResponseDto newArticleDto = new ArticleResponseDto(savedArticle);
        return new ResponseEntity<>(newArticleDto, HttpStatus.CREATED);
    }

    // 게시물 수정
    @PutMapping("/{id}")
    public ResponseEntity<ArticleResponseDto> updateArticle(@PathVariable Long id,
                                                            @RequestBody ArticleRequestDto articleDto) {
        Article article = new Article();
        article.setTitle(articleDto.getTitle());
        article.setContent(articleDto.getContent());
        // TODO: 인가 확인 필요
        Article updatedArticle = articleService.updateArticle(id, article);
        ArticleResponseDto updatedArticleDto = new ArticleResponseDto(updatedArticle);
        return new ResponseEntity<>(updatedArticleDto, HttpStatus.OK);
    }

    // 게시물 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        // TODO: 인가 확인 필요
        articleService.deleteArticle(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
