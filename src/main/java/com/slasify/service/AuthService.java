package com.slasify.service;

import com.slasify.dto.request.UserRegisterReq;
import com.slasify.dto.response.UserRegisterRes;

public interface AuthService {
    UserRegisterRes register(UserRegisterReq userRegisterReq);
    String authenticate(String identifier, String password, boolean rememberMe);
}
