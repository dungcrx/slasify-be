package com.slasify.controller;

import com.slasify.dto.request.MessageReq;
import com.slasify.dto.response.MessageRes;
import com.slasify.entity.Message;
import com.slasify.filter.RequestInfo;
import com.slasify.repository.MessageRepository;
import com.slasify.service.MessageService;
import com.slasify.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageRepository messageRepo;

    private final SimpMessagingTemplate messagingTemplate;

    private final MessageService messageService;

    private final RequestInfo requestInfo;


    @GetMapping("/all")
    public ResponseEntity<List<MessageRes>> getAllMessages(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size) {
        List<MessageRes> messages = messageService.getAllMessages(page, size);
        return ResponseEntity.ok(messages);
    }

    @PostMapping("/post")
    public ResponseEntity<?> postMessage(@RequestBody MessageReq request) {
        String content = request.getContent();
        String username = SecurityUtil.getCurrentUsername();
        if (username == null) {
            throw new IllegalStateException("User not authenticated");
        }
        messageService.postMessage(content, requestInfo.getUserId());
        return ResponseEntity.ok("Message posted");
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateMessage(@RequestParam Long messageId, @RequestBody String newContent) {
        Message message = messageRepo.findById(messageId).orElseThrow();
        message.setContent(newContent);
        messageRepo.save(message);
        return ResponseEntity.ok("Message updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMessage(@PathVariable Long id) {
        messageRepo.deleteById(id);
        return ResponseEntity.ok("Message deleted");
    }
}

