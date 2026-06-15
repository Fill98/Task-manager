package com.example.taskmaneger.dtos.taskdto;

public record CreateTaskDto(String taskName, String description, Long userId, Long assignedById) {
}
