package com.ckarousis.eshopapp.services;

import com.ckarousis.eshopapp.model.LoginRequest;
import com.ckarousis.eshopapp.model.User;
import com.ckarousis.eshopapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService{
    @Autowired
    private UserRepository userRepository;

    public Optional<User> getUserById(Long id){
        return userRepository.findById(id);
    }
    public List<User> getAllUsers(){return userRepository.findAll();}

    public Optional<User> getUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public User register(User user) {
        // Check if the email or username already exists
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already registered!");
        }
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username already taken!");
        }
        //System.out.println("HERE");

        // If no duplicates, save the user
        //List<String> roles = new ArrayList<>();
        //roles.add("ROLE_USER");
        //user.setRoles(roles);
        //System.out.println("HEREeeeeeeeeeeeeeeeeee");
        return userRepository.save(user);
    }

    public User authenticate(LoginRequest loginRequest) {
        boolean isEmail = loginRequest.getLogin().contains("@");
        Optional<User> userOptional = isEmail
                ? userRepository.findByEmail(loginRequest.getLogin())
                : userRepository.findByUsername(loginRequest.getLogin());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (loginRequest.getPassword().equals(user.getPassword())) {
                System.out.println("Pass done");
                return user;
            } else {
                throw new RuntimeException("Invalid credentials");
            }
        } else {
            throw new RuntimeException("User not found with login: " + loginRequest.getLogin());
        }
    }
    public void deleteUserById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User with this id does not exist.");
        }

        userRepository.deleteById(id);
    }
}
