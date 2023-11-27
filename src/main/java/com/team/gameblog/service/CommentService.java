package com.team.gameblog.service;

import com.team.gameblog.entity.Comment;
import com.team.gameblog.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;


    public Optional<Comment> findById(Long id) {
        return commentRepository.findById(id);
    }

    // 댓글 생성
    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    // 댓글 수정
    public Comment updateComment(Long id, Comment updatedComment) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        comment.setComment(updatedComment.getComment()); // 댓글 내용 업데이트
        return commentRepository.save(comment);
    }

    // 댓글 삭제
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

    // 특정 게시물의 댓글 조회
    public List<Comment> getCommentsByArticleId(Long articleId) {
        return commentRepository.findByArticleId(articleId);
    }

}