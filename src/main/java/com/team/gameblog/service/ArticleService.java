package com.team.gameblog.service;

import com.team.gameblog.entity.Article;
import com.team.gameblog.exception.ResourceNotFoundException;
import com.team.gameblog.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    // 생성자 주입을 사용합니다.
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> getAllArticles() {
        return articleRepository.findAllByOrderByCreatedAtDesc();
    }

    public Article getArticleById(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found with id " + id));
    }

    public Article createArticle(Article article) {
        return articleRepository.save(article);
    }

    public Article updateArticle(Long id, Article articleDetails) {
        Article article = getArticleById(id);
        article.setTitle(articleDetails.getTitle());
        article.setContent(articleDetails.getContent());
        return articleRepository.save(article);
    }

    public void deleteArticle(Long id) {
        Article article = getArticleById(id);
        articleRepository.delete(article);
    }
}
