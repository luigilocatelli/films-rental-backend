package com.training.service;


import com.training.models.security.UserDTO;

import java.util.Optional;

public interface UserService {
    Optional<UserDTO> findUserByUsername(String username);
}
