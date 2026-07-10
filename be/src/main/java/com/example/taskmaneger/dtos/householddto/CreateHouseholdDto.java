package com.example.taskmaneger.dtos.householddto;

import jakarta.validation.constraints.NotBlank;

public record CreateHouseholdDto (@NotBlank(message = "Household name must not by empty") String name){
}
