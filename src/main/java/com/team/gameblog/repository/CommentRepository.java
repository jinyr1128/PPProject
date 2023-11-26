package com.team.gameblog.repository;

import com.team.gameblog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // 특정 게시물의 모든 댓글을 조회하는 메소드
    List<Comment> findByArticleId(Long articleId);
}