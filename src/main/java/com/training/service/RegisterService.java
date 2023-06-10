package com.training.service;


import com.training.models.security.request.RegisterRequest;
import com.training.models.security.response.RegisterResponse;

public interface RegisterService {
    RegisterResponse doRegister(RegisterRequest registerRequest);

}
