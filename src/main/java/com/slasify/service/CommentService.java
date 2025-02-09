package com.slasify.service;

import com.slasify.dto.response.CommentRes;
import com.slasify.entity.Comment;

import java.util.List;

public interface CommentService {

    CommentRes postComment(Long messageId, Long parentCommentId, String content, Long userId);
    List<Comment> getCommentsByMessage(Long messageId);
}
