package com.example.taskmaneger.persistence.repository;

import com.example.taskmaneger.persistence.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User,Long> {
    Optional <User> findByUsername(String username);
}
