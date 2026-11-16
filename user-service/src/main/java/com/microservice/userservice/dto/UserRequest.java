package com.microservice.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequest(

        @NotBlank(message = "Name is required")
        @Size(max = 100,message = "Name can't exceed 100 character")
        String name,

        @Email(message = "Email is required")
        @Email(message = "Please provide valid email")
        @Size(max = 150,message = "Email can't exceed 150 character")
        String email,

        @Size(max = 20,message = "phone number can't exceed 20 character")
        String phone

) {
}
