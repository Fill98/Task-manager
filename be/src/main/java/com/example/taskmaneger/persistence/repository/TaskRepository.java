package com.example.taskmaneger.persistence.repository;

import com.example.taskmaneger.persistence.entity.Status;
import com.example.taskmaneger.persistence.entity.Task;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TaskRepository extends CrudRepository<Task, Long> {
    //pomocna metoda na hladanie uloh podls domacnosti
    List<Task> findByHouseholdId(Long householdId);
    //zoradenie podla teminu
    List<Task> findAllByOrderByMustBeDoneAsc();
    //filtrovanie podla statusu
    List<Task> findByStatus(Status status);
}