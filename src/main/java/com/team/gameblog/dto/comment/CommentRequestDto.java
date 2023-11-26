package com.team.gameblog.dto.comment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {
    private String comment; // 댓글 내용
    private Long articleId; // 게시글 ID (댓글을 달 게시글)
    private Long userId;// 사용자 ID (댓글 작성자)
}
