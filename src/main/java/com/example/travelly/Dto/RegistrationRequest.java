package com.example.travelly.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class RegistrationRequest {
    @NotEmpty
    private String fullName;
    @Email
    @NotEmpty
    private String email;
    @NotEmpty
    @Length(min = 6, max = 16, message = "Please enter password of atleast 6 characters")
    private String password;
}
