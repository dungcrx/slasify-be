package com.slasify.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.List;


@Component
public class RequestInfoFilter extends GenericFilterBean {
    @Autowired private RequestInfoImpl requestInfo;

    List<String> apiUnAuth = List.of("/register", "/h2-console", "/login","/messages/all");

    @Override
    public void doFilter(
            ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String requestURI = request.getRequestURI();
        if (!apiUnAuth.stream().anyMatch(requestURI::contains)) {
            requestInfo.init();
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}