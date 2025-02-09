package com.slasify.dto.request;

import lombok.Data;

@Data
public class CommentReq {
    private Long messageId;
    private Long parentCommentId;
    private String content;
}
