package com.team.gameblog.controller;


import com.team.gameblog.dto.comment.CommentResponseDto;
import com.team.gameblog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    // 댓글 생성
    @PostMapping("/comments/{id}")
    public CommentResponseDto createComment(){

        return commentService.createComment();

    }

    //댓글 수정
    @PutMapping("/comments/{id}")
    public CommentResponseDto updateComment(){

        return commentService.updateComment();

    }

    //댓글 삭제
    @DeleteMapping("/comments/{id}")
    public void deleteComment(){

        commentService.deleteComment();

    }


}
