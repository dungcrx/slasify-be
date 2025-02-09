package com.slasify.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class MessageRes {
    private Long id;
    private String content;
    private String username;
    private LocalDateTime postingTime;
    private List<CommentRes> comments;
}
