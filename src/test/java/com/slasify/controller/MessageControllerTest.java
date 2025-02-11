package com.slasify.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.slasify.dto.request.MessageReq;
import com.slasify.filter.RequestInfoImpl;
import com.slasify.repository.MessageRepository;
import com.slasify.service.MessageService;
import com.slasify.service.impl.AuthServiceImpl;
import com.slasify.utils.SecurityUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(MessageController.class)
@ExtendWith(SpringExtension.class)
public class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MessageRepository messageRepo;

    @MockBean
    private SimpMessagingTemplate messagingTemplate;

    @MockBean
    private MessageService messageService;

    @MockBean(name = "requestInfoImpl")
    private RequestInfoImpl requestInfoImpl;



    @MockBean
    private AuthServiceImpl authServiceImpl;

    @Test
    public void testPostMessage_WhenUnauthorizedUser_ForbiddenResponse() throws Exception {
        String content = "Test message content";
        String username = "testUser";
        Long userId = 1L;

        MessageReq messageReq = new MessageReq();
        messageReq.setContent(content);
        Mockito.mockStatic(SecurityUtil.class).when(SecurityUtil::getCurrentUsername).thenReturn(username);
        when(requestInfoImpl.getUserId()).thenReturn(userId);

        mockMvc.perform(post("/messages/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(messageReq)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles = "USER")
    public void testPostMessage_Success() throws Exception {
        when(authServiceImpl.getUserId()).thenReturn(123L);
        mockMvc.perform(post("/messages/post")
                        .with(user("username").password("password").roles("USER"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"content\":\"这些称呼在中文中非常重要\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Message posted"));
    }




}
