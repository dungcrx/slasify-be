package com.slasify.utils;

import com.slasify.dto.response.CommentRes;
import com.slasify.entity.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentUtil {
    public static CommentRes buildCommentDTO(Comment comment) {
        CommentRes res = CommentRes.builder()
                .id(comment.getId())
                .postingTime(comment.getPostingTime())
                .content(comment.getContent())
                .username(comment.getUsername())
                .build();

        List<CommentRes> replyDTOs = new ArrayList<>();
        for (Comment reply : comment.getReplies()) {
            replyDTOs.add(buildCommentDTO(reply));
        }
        res.setReplies(replyDTOs);
        return res;
    }
}
