package com.slasify.service.impl;

import com.slasify.dto.response.CommentRes;
import com.slasify.dto.response.MessageRes;
import com.slasify.dto.response.PostMessageRes;
import com.slasify.entity.Comment;
import com.slasify.entity.Message;
import com.slasify.entity.User;
import com.slasify.exception.MessageNotFoundException;
import com.slasify.exception.UserNotFoundException;
import com.slasify.repository.CommentRepository;
import com.slasify.repository.MessageRepository;
import com.slasify.repository.UserRepository;
import com.slasify.service.MessageService;
import com.slasify.utils.CommentUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;


    public PostMessageRes postMessage(final String content,
                                      final Long userId) {
        if (content.length() < 3 || content.length() > 200) {
            throw new IllegalArgumentException("Message must be between 3 and 200 characters.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Message message = new Message();
        message.setContent(content);
        message.setUsername(user.getUsername() != null ? user.getUsername() : user.getEmail());
        message.setPostingTime(LocalDateTime.now());
        message = messageRepository.save(message);
        return PostMessageRes.builder()
                .id(message.getId())
                .createdAt(message.getPostingTime())
                .build();
    }

    @Override
    public List<MessageRes> getAllMessages(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("postingTime").descending());
        Page<Message> messages = messageRepository.findAll(pageable);
        List<MessageRes> messageRes = new ArrayList<>();

        for (Message message : messages) {
            MessageRes msg = new MessageRes();
            msg.setId(message.getId());
            msg.setContent(message.getContent());
            msg.setUsername(message.getUsername());
            msg.setPostingTime(message.getPostingTime());

            List<CommentRes> commentDTOs = new ArrayList<>();
            List<Comment> comments = message.getComments();

            for (Comment comment : comments) {
                if(comment.getParentComment() == null)
                    commentDTOs.add(CommentUtil.buildCommentDTO(comment));
            }

            msg.setComments(commentDTOs);
            messageRes.add(msg);
        }

        return messageRes;
    }



    public void deleteMessage(Long messageId, Long userId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new MessageNotFoundException("Message not found"));


        messageRepository.delete(message);
    }


    public List<Message> getTopLevelMessages() {
        return messageRepository.findAllByOrderByPostingTimeDesc();
    }


    public Optional<Message> findById(Long id) {
        return messageRepository.findById(id);
    }
}

