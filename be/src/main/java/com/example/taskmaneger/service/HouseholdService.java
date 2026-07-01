package com.example.taskmaneger.service;

import com.example.taskmaneger.dtos.householddto.CreateHouseholdDto;
import com.example.taskmaneger.dtos.householddto.HouseholdDto;
import com.example.taskmaneger.persistence.entity.Household;
import com.example.taskmaneger.persistence.entity.User;
import com.example.taskmaneger.persistence.repository.HouseholdRepository;
import com.example.taskmaneger.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HouseholdService {

    @Autowired
    private HouseholdRepository householdRepository;
    @Autowired
    private UserRepository userRepository;

    //metoda na vytvorenie domacnosti
    public HouseholdDto createHousehold(CreateHouseholdDto createHouseholdDto){
        Household household = new Household();
        household.setName(createHouseholdDto.name());

        User owner =
                userRepository.findById(createHouseholdDto.ownerId())
                .orElseThrow(() -> new RuntimeException("owner household not found"));
        household.setOwner(owner);
        householdRepository.save(household);

        return new HouseholdDto(household.getId(), owner.getId(), household.getName());
    }



}
