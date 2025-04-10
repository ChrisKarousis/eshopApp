package com.ckarousis.eshopapp.services;

import com.ckarousis.eshopapp.model.User;
import com.ckarousis.eshopapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public User register(User user){
        return userRepository.save(user);
    }

    public User authenticate(String email, String password) {
        System.out.println("Email : " + email);
        System.out.println("Pass : " + password);
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (password.equals(user.getPassword())) {
                System.out.println("Pass done");
                return user;
            } else {
                throw new RuntimeException("Invalid credentials");
            }
        } else {
            throw new RuntimeException("User not found with email: " + email);
        }
    }
}
