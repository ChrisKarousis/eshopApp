package com.ckarousis.eshopapp.services;

import com.ckarousis.eshopapp.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> getUserById(Long id);
    List<User> getAllUsers();
    User register(User user);
    User authenticate(String email, String password);
    void deleteUserById(Long id);
}
