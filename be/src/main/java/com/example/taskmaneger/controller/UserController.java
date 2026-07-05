package com.example.taskmaneger.controller;

import com.example.taskmaneger.dtos.userdto.CreateUserDto;
import com.example.taskmaneger.dtos.userdto.UserDto;
import com.example.taskmaneger.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    UserService userService;


    @PostMapping("/api/user")
    public UserDto createUser(@Valid @RequestBody CreateUserDto createUserDto){
        return userService.createUser(createUserDto);
    }

    @DeleteMapping("/api/user/{id}")
    public void deleteUser(@PathVariable long id){
        userService.deleteUser(id);
    }

    @GetMapping("/api/user/{username}")
    public UserDto findUserByUsername(@PathVariable String username){
        return userService.findUserByUsername(username);
    }
    //vstup a vystup FE/BE na najdenie usera podla id
    @GetMapping("/api/user/id/{id}")
    public UserDto findUserById(@PathVariable long id){
        return userService.findUserById(id);
    }

}
