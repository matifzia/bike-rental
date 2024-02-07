package com.atifzia.bikerental.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import com.atifzia.bikerental.dtos.UserRequestDTO;
import com.atifzia.bikerental.models.User;

public interface UserService extends UserDetailsService {
    User registerUser(UserRequestDTO user);
    String loginUser(String username, String password);
}
