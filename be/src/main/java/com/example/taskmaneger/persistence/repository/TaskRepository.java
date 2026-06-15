package com.example.taskmaneger.persistence.repository;

import com.example.taskmaneger.persistence.entity.Task;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, Long> {
}
