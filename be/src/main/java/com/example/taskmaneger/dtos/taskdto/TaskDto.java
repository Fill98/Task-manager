package com.example.taskmaneger.dtos.taskdto;

public record TaskDto(Long id, String taskName, String description, String assignedTo, String assignedBy) {
}
