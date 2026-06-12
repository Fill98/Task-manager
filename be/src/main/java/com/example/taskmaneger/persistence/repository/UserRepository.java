package com.example.taskmaneger.persistence.repository;

import com.example.taskmaneger.persistence.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {
}
