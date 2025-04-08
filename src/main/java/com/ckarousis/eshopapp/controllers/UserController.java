package com.ckarousis.eshopapp.controllers;

import com.ckarousis.eshopapp.model.User;
import com.ckarousis.eshopapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/eshop/users")
public class UserController {
    @Autowired
    private UserService userService;

    /*
    @GetMapping()
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

     */

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id).orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
    }
}
