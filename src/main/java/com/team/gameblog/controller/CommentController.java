package com.team.gameblog.controller;

import com.team.gameblog.dto.comment.CommentRequestDto;
import com.team.gameblog.dto.comment.CommentResponseDto;
import com.team.gameblog.entity.Article;
import com.team.gameblog.entity.Comment;
import com.team.gameblog.exception.ResourceNotFoundException;
import com.team.gameblog.repository.ArticleRepository;
import com.team.gameblog.security.UserDetailsImpl;
import com.team.gameblog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final ArticleRepository articleRepository;

    // 댓글 생성
    @PostMapping("/comments/{articleId}")
    public CommentResponseDto createComment(@PathVariable Long articleId, @RequestBody CommentRequestDto commentRequestDto, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Comment comment = new Comment();
        comment.setComment(commentRequestDto.getComment());
        comment.setUser(userDetails.getUser());

        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found"));
        comment.setArticle(article);

        Comment savedComment = commentService.createComment(comment);

        return convertToCommentResponseDto(savedComment);
    }

    // 댓글 수정
    @PutMapping("/comments/{commentId}")
    public CommentResponseDto updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto commentRequestDto, Authentication authentication) {
        Comment existingComment = commentService.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        if (!existingComment.getUser().getId().equals(userDetails.getId())) {
            throw new RuntimeException("Unauthorized");
        }
        existingComment.setComment(commentRequestDto.getComment());
        Comment updatedComment = commentService.updateComment(commentId, existingComment);

        return convertToCommentResponseDto(updatedComment);
    }

    // 댓글 삭제
    @DeleteMapping("/comments/{commentId}")
    public void deleteComment(@PathVariable Long commentId, Authentication authentication) {
        Comment existingComment = commentService.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        if (!existingComment.getUser().getId().equals(userDetails.getId())) {
            throw new RuntimeException("Unauthorized");
        }
        commentService.deleteComment(commentId);
    }

    // CommentResponseDto로 변환하는 메소드
    private CommentResponseDto convertToCommentResponseDto(Comment comment) {
        CommentResponseDto dto = new CommentResponseDto();
        dto.setId(comment.getId());
        dto.setComment(comment.getComment());
        dto.setArticleId(comment.getArticle().getId());
        dto.setUserId(comment.getUser().getId());
        return dto;
    }
}
