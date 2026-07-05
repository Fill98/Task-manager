package com.example.taskmaneger.dtos.userdto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserDto(@NotBlank(message = "Username cannot be empty.") String username, @Size(min = 8, message = "Password must be at least 8 characters long.")@NotBlank(message = "Password is required.") String password){
}
