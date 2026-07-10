package com.example.taskmaneger.security;

import com.example.taskmaneger.exception.NotFoundException;
import com.example.taskmaneger.persistence.entity.User;
import com.example.taskmaneger.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentUserService {

    @Autowired
    private UserRepository userRepository;

    public User getCurrentUser(){
        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        return userRepository.findByUsername(username)
                .orElseThrow(()-> new NotFoundException("User not found."));
    }
}
