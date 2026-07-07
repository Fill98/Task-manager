package com.example.taskmaneger.service;

import com.example.taskmaneger.dtos.userdto.CreateUserDto;
import com.example.taskmaneger.dtos.userdto.UserDto;
import com.example.taskmaneger.exception.ConflictException;
import com.example.taskmaneger.exception.NotFoundException;
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
        if(userRepository.findByUsername(createUserDto.username()).isPresent()){
            throw new ConflictException("Username is already taken.");
        }
        User user = new User();
        user.setUsername(createUserDto.username());
        user.setPassword(passwordEncoder.encode(createUserDto.password()));
        userRepository.save(user);

        return new UserDto(user.getId(), user.getUsername());
    }

    public void deleteUser(long id){
        userRepository.deleteById(id);
    }
    //TODO modify user


    public UserDto findUserByUsername(String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new NotFoundException("User not found."));

        return new UserDto(user.getId(),user.getUsername());
    }
    //metoda na najdenie usera podla id
    public UserDto findUserById(long id){
        User user = userRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("User not found."));

        return new UserDto(user.getId(),user.getUsername());
    }
}
