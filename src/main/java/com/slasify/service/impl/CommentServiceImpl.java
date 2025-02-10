package com.slasify.service.impl;

import com.slasify.dto.response.CommentRes;
import com.slasify.entity.Comment;
import com.slasify.entity.Message;
import com.slasify.entity.User;
import com.slasify.exception.UserNotFoundException;
import com.slasify.repository.CommentRepository;
import com.slasify.repository.MessageRepository;
import com.slasify.repository.UserRepository;
import com.slasify.service.CommentService;
import com.slasify.utils.CommentUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final MessageRepository messageRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    @Override
    public CommentRes postComment(Long messageId,
                                  Long parentCommentId,
                                  String content,
                                  Long userId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new IllegalArgumentException("Message not found"));

        User userComment = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setUsername(userComment.getUsername() != null ? userComment.getUsername() : userComment.getEmail());
        comment.setPostingTime(LocalDateTime.now());
        comment.setMessage(message);

        if (parentCommentId != null) {
            Comment parentComment = commentRepository.findById(parentCommentId)
                    .orElseThrow(() -> new IllegalArgumentException("Parent comment not found"));
            comment.setParentComment(parentComment);
        }

        comment = commentRepository.save(comment);
        return CommentUtil.buildCommentDTO(comment);

    }

    @Override
    public List<Comment> getCommentsByMessage(Long messageId) {
        return commentRepository.findByMessageId(messageId);
    }
}
