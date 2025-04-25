package com.ckarousis.eshopapp.model;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDTO {
    @NotEmpty
    private String username;

    @NotEmpty
    @Email
    private String email;

    private String address;

    @Size(min=6, message="Minimum Password length is 6 characters")
    private String password;

    private String confirmPassword;
}
