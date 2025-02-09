package com.slasify.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
@Data
@Builder
public class CommentRes {
    private Long id;
    private String content;
    private String username;
    private LocalDateTime postingTime;
    private List<CommentRes> replies;
}
