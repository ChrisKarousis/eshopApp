package com.ckarousis.eshopapp.controllers;

import com.ckarousis.eshopapp.model.LoginRequest;
import com.ckarousis.eshopapp.model.User;
import com.ckarousis.eshopapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        User user = userService.getUserById(id).orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
        return user;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    //@CrossOrigin(origins = "http://localhost:8080")
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginRequest loginRequest){
        try {
            System.out.println(loginRequest.getLogin());
            User user = userService.authenticate(loginRequest);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user){
        try {
            User user2 = userService.register(user);
            return new ResponseEntity<>(user2, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok("User deleted");
    }
}
