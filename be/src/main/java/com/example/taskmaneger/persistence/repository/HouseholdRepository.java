package com.example.taskmaneger.persistence.repository;

import com.example.taskmaneger.persistence.entity.Household;
import org.springframework.data.repository.CrudRepository;

public interface HouseholdRepository extends CrudRepository<Household,Long> {
}
