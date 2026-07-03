package com.example.taskmaneger.service;

import com.example.taskmaneger.dtos.householddto.CreateHouseholdDto;
import com.example.taskmaneger.dtos.householddto.HouseholdDto;
import com.example.taskmaneger.persistence.entity.Household;
import com.example.taskmaneger.persistence.entity.User;
import com.example.taskmaneger.persistence.repository.HouseholdRepository;
import com.example.taskmaneger.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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
        //vytvreme prazdny list clenov domacnosti
        household.setMembers(new ArrayList<>());
        //pridame ownera ako clena
        household.getMembers().add(owner);
        householdRepository.save(household);

        return new HouseholdDto(household.getId(), owner.getId(), household.getName());
    }
    //metoda na najdenie domacnosti podla id
    public HouseholdDto findHouseholdById(Long householdId){
        Household household = householdRepository.findById(householdId)
                .orElseThrow(() -> new RuntimeException("household not found"));

        return new HouseholdDto(household.getId(), household.getOwner().getId(), household.getName());

    }
    //pridanie clena domacnosti
    public HouseholdDto addMember(Long householdId, Long userId){
        Household household = householdRepository.findById(householdId)
                .orElseThrow(() -> new RuntimeException("household not found"));
        User newMember = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found"));
        //overenie ci zoznam existuje ak nie vytvor novy
        if (household.getMembers() == null){
            household.setMembers(new ArrayList<>());
        }
        household.getMembers().add(newMember);

        householdRepository.save(household);

        return new HouseholdDto(household.getId(),household.getOwner().getId(),  household.getName());
    }



}
