package com.team.gameblog.controller;

import com.team.gameblog.dto.article.MyArticleResponseDto;
import com.team.gameblog.dto.article.SelectArticleResponseDto;
import com.team.gameblog.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//html페이지 자체 리턴 안할경우 @RestController 변경???????
@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    //글 생성
    @PostMapping("/articles")
    public SelectArticleResponseDto createArticle(){

        return articleService.createArticle();

    }

    // MY글 전체 조회
    @GetMapping("/articles")
    public List<MyArticleResponseDto> getMyArticles(){

        return articleService.getMyArticles();

    }

    // 특정글 조회
    @GetMapping("/articles/{id}")
    public SelectArticleResponseDto getArticle(){

        return articleService.getArticle();

    }

    // MY글 수정
    @PutMapping("/articles/{id}")
    public SelectArticleResponseDto updateMyArticle() {

        return articleService.updateMyArticle();

    }

    // My글 삭제
    @DeleteMapping("/articles/{id}")
    public void deleteMyArticle(){

        articleService.deleteMyArticle();

    }


}
