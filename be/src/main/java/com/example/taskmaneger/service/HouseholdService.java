package com.example.taskmaneger.service;

import com.example.taskmaneger.dtos.householddto.CreateHouseholdDto;
import com.example.taskmaneger.dtos.householddto.HouseholdDto;
import com.example.taskmaneger.exception.ConflictException;
import com.example.taskmaneger.exception.ForbiddenException;
import com.example.taskmaneger.exception.NotFoundException;
import com.example.taskmaneger.persistence.entity.Household;
import com.example.taskmaneger.persistence.entity.User;
import com.example.taskmaneger.persistence.repository.HouseholdRepository;
import com.example.taskmaneger.persistence.repository.UserRepository;
import com.example.taskmaneger.security.CurrentUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class HouseholdService {

    @Autowired
    private HouseholdRepository householdRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CurrentUserService currentUserService;

    //metoda na vytvorenie domacnosti
    public HouseholdDto createHousehold(CreateHouseholdDto createHouseholdDto){
        User owner = currentUserService.getCurrentUser();

        //overenie ci user uz neni clenom/ownerom inej domacnosti
        if (householdRepository.existsByMembers_Id(owner.getId())){
            throw new ConflictException("User is already a member of a household.");
        }

        Household household = new Household();
        household.setName(createHouseholdDto.name());

        household.setOwner(owner);
        //vytvarame prazdny list clenov domacnosti
        household.setMembers(new ArrayList<>());
        //pridame ownera ako clena
        household.getMembers().add(owner);
        householdRepository.save(household);

        return new HouseholdDto(household.getId(), owner.getId(), household.getName());
    }
    //metoda na najdenie domacnosti podla id
    public HouseholdDto findHouseholdById(Long householdId){
        Household household = householdRepository.findById(householdId)
                .orElseThrow(() -> new NotFoundException("Household not found."));

        return new HouseholdDto(household.getId(), household.getOwner().getId(), household.getName());

    }
    //pridanie clena domacnosti
    public HouseholdDto addMember(Long householdId, Long userId){
        User user = currentUserService.getCurrentUser();

        Household household = householdRepository.findById(householdId)
                .orElseThrow(() -> new NotFoundException("Household not found."));
        if(!user.getId().equals(household.getOwner().getId())){
            throw new ForbiddenException("You are not an owner of the household.");
        }
        User newMember = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found."));

        //overenie ci user uz neni clen uz inej domacnosti
        if (householdRepository.existsByMembers_Id(userId)){
            throw new ConflictException("User is already a member of a household.");
        }
        //overenie ci zoznam existuje ak nie vytvor novy
        if (household.getMembers() == null){
            household.setMembers(new ArrayList<>());
        }
        household.getMembers().add(newMember);

        householdRepository.save(household);

        return new HouseholdDto(household.getId(),household.getOwner().getId(),  household.getName());
    }


    //metoda na odstranenie clena domacnosti(userom samim alebo ownerom domacnosti)
    public void removeMember(Long householdId, Long userId){
        Household household = householdRepository.findById(householdId)
                .orElseThrow(() -> new NotFoundException("Household not found."));

        User user = currentUserService.getCurrentUser();
        boolean isOwner = household.getOwner().getId().equals(user.getId());
        boolean removingSelf = user.getId().equals(userId);

        if(!isOwner && !removingSelf){
            throw new ForbiddenException("You can only remove yourself, or owner can remove members");
        }

        household.getMembers().removeIf(member -> member.getId().equals(userId));
        householdRepository.save(household);
    }



}
