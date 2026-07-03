package com.example.taskmaneger.controller;

import com.example.taskmaneger.dtos.householddto.CreateHouseholdDto;
import com.example.taskmaneger.dtos.householddto.HouseholdDto;
import com.example.taskmaneger.service.HouseholdService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class HouseholdController {

    @Autowired
    private HouseholdService householdService;


    //spojenie FE/BE

    //metoda na tvorbu domacnosti
    @PostMapping("/api/household")
    public HouseholdDto createHousehold(@Valid @RequestBody CreateHouseholdDto createHouseholdDto) {
        return householdService.createHousehold(createHouseholdDto);
    }
    //vrati domacnost podla id
    @GetMapping("/api/household/{householdId}")
    public HouseholdDto getHousehold(@PathVariable("householdId") Long householdId) {
        return householdService.findHouseholdById(householdId);
    }

    //pridanie clena domacnosti
    @PostMapping("/api/household/{householdId}/members/{userId}")
    public HouseholdDto addMember(@PathVariable("householdId") Long householdId, @PathVariable("userId") Long userId) {
        return householdService.addMember(householdId, userId);
    }

    //odstranenie clena domacnosti(userom samim alebo ownerom)
    //TODO: treba dorobit overenie opravnenia mazat
    @DeleteMapping("/api/household/{householdId}/members/{userId}")
    public void  removeMember(@PathVariable("householdId") Long householdId, @PathVariable("userId") Long userId){
        householdService.removeMember(householdId, userId);
    }

}
