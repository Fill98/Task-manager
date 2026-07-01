package com.example.taskmaneger.controller;

import com.example.taskmaneger.dtos.householddto.CreateHouseholdDto;
import com.example.taskmaneger.dtos.householddto.HouseholdDto;
import com.example.taskmaneger.service.HouseholdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class HouseholdController {

    @Autowired
    private HouseholdService householdService;


    //spojenie FE/BE

    //metoda na tvorbu domacnosti
    @PostMapping("/api/household")
    public HouseholdDto createHousehold(@RequestBody CreateHouseholdDto createHouseholdDto) {
        return householdService.createHousehold(createHouseholdDto);
    }
    //vrati domacnost podla id
    @GetMapping("/api/household/{householdId}")
    public HouseholdDto getHousehold(@PathVariable("householdId") Long householdId) {
        return householdService.findHouseholdById(householdId);
    }

}
