package com.slasify.service;

import com.slasify.dto.response.MessageRes;
import com.slasify.dto.response.PostMessageRes;

import java.util.List;

public interface MessageService {
    PostMessageRes postMessage(String content, Long userId);
    List<MessageRes> getAllMessages(int pageNumber, int pageSize);
}
