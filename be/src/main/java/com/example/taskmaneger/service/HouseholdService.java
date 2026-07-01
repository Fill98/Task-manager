package com.example.taskmaneger.service;

import com.example.taskmaneger.persistence.repository.HouseholdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HouseholdService {

    @Autowired
    private HouseholdRepository householdRepository;


}
