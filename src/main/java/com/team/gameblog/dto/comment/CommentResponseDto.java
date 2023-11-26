package com.team.gameblog.dto.comment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentResponseDto {
    private Long id;       // 댓글 ID
    private String comment; // 댓글 내용
    private Long articleId; // 게시글 ID
    private Long userId;    // 사용자 ID
}
