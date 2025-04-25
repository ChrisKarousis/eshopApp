package com.ckarousis.eshopapp.controllers;

import com.ckarousis.eshopapp.model.RegisterDTO;
import com.ckarousis.eshopapp.model.User;
import com.ckarousis.eshopapp.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class AccountController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/register")
    public String register(Model model){
        RegisterDTO registerDTO = new RegisterDTO();
        model.addAttribute(registerDTO);
        model.addAttribute("success", false);
        return "register";
    }

    @PostMapping("/register")
    public String register(
            Model model,
            @Valid @ModelAttribute RegisterDTO registerDTO,
            BindingResult result
    ){
        if(!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())){
            result.addError(
                    new FieldError("registerDto", "confirmPassword",
                            "Password and Confirm Password do not match")
            );
        }

        Optional<User> user = userRepository.findByEmail(registerDTO.getEmail());
        if(user.isPresent()){
            User appUser = user.get();
            result.addError(
                    new FieldError("registerDto", "email",
                            "Email address is already used")
            );
        }

        if(result.hasErrors()){
            return "register";
        }

        try{
            var bCryptEncoder = new BCryptPasswordEncoder();
            User newUser = new User();
            newUser.setUsername(registerDTO.getUsername());
            newUser.setEmail(registerDTO.getEmail());
            newUser.setRole("client");
            newUser.setPassword(bCryptEncoder.encode(registerDTO.getPassword()));
            newUser.setAddress(registerDTO.getAddress());
            userRepository.save(newUser);

            model.addAttribute("registerDto", new RegisterDTO());
            model.addAttribute("success", true);
        }catch(Exception ex){
            result.addError(
                    new FieldError("registerDto", "username", ex.getMessage())
            );
        }
        return "register";
    }
}
