package com.ckarousis.eshopapp.services;

import com.ckarousis.eshopapp.model.LoginRequest;
import com.ckarousis.eshopapp.model.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> getUserById(Long id);
    List<User> getAllUsers();
    Optional<User> getUserByEmail(String email);
    Optional<User> getUserByUsername(String username);
    //User register(User user);
    User authenticate(LoginRequest loginRequest);
    void deleteUserById(Long id);
    UserDetails loadUserByUsername(String username);
}
