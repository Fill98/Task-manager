package com.example.taskmaneger.controller;

import com.example.taskmaneger.dto.CreateUserDto;
import com.example.taskmaneger.dto.UserDto;
import com.example.taskmaneger.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserControler {

    @Autowired
    UserService userService;

    //TODO createUser
    @PostMapping("/api/user")
    public UserDto createUser(@RequestBody CreateUserDto createUserDto){
        return userService.createUser(createUserDto);
    }

    @DeleteMapping("/api/user")
    public void deleteUser(long id){
        userService.deleteUser(id);
    }

    //TODO modifyUser



    //TODO findUSer

}
