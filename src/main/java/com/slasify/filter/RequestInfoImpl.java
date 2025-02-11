package com.slasify.filter;

import com.slasify.service.impl.AuthServiceImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;


@Primary
@RequestScope
@Component
@Getter
@RequiredArgsConstructor
public class RequestInfoImpl implements RequestInfo {
    private final AuthServiceImpl authServiceImpl;
    private Long userId;
    public void init() {
        this.userId = authServiceImpl.getUserId();
    }
}
