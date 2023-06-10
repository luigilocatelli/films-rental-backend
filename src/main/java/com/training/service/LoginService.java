package com.training.service;


import com.training.models.security.request.LoginRequest;
import com.training.models.security.response.LoginResponse;

public interface LoginService {

    LoginResponse doLogin(LoginRequest loginRequest);
}
