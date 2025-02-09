package com.slasify.controller;

import com.slasify.dto.request.CommentReq;
import com.slasify.dto.response.CommentRes;
import com.slasify.entity.Comment;
import com.slasify.filter.RequestInfo;
import com.slasify.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final RequestInfo requestInfo;
    @PostMapping
    public ResponseEntity<CommentRes> postComment(@RequestBody CommentReq request) {
        CommentRes res = commentService.postComment(
                request.getMessageId(),
                request.getParentCommentId(),
                request.getContent(),
                requestInfo.getUserId());

        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @GetMapping("/{messageId}")
    public ResponseEntity<List<Comment>> getComments(@PathVariable Long messageId) {
        return ResponseEntity.ok(commentService.getCommentsByMessage(messageId));
    }
}
