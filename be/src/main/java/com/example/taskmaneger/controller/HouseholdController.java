package com.example.taskmaneger.controller;

import com.example.taskmaneger.dtos.householddto.CreateHouseholdDto;
import com.example.taskmaneger.dtos.householddto.HouseholdDto;
import com.example.taskmaneger.service.HouseholdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HouseholdController {

    @Autowired
    private HouseholdService householdService;


    //spojenie FE/BE
    @PostMapping("/api/household")
    public HouseholdDto createHousehold(@RequestBody CreateHouseholdDto createHouseholdDto) {
        return householdService.createHousehold(createHouseholdDto);
    }

}
