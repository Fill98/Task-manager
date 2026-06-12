package com.example.taskmaneger.service;

import com.example.taskmaneger.dto.CreateUserDto;
import com.example.taskmaneger.dto.UserDto;
import com.example.taskmaneger.persistence.entity.User;
import com.example.taskmaneger.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public UserDto createUser(CreateUserDto createUserDto){
        User user = new User();
        user.setUsername(createUserDto.username());
        user.setPassword(passwordEncoder.encode(createUserDto.password()));
        userRepository.save(user);

        return new UserDto(user.getId(), user.getUsername());
    }


    //TODO DELETE user



    //TODO modify user



    //TODO find user
}
