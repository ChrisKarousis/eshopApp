package com.ckarousis.eshopapp.services;

import com.ckarousis.eshopapp.model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> getUserById(Long id);
    User register(User user);
    User authenticate(String email, String password);
}
