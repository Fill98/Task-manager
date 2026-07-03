package com.example.taskmaneger.dtos.taskdto;



import com.example.taskmaneger.persistence.entity.Priority;
import com.example.taskmaneger.persistence.entity.Status;

import java.time.LocalDateTime;

public record CreateTaskDto(String taskName, String description, Long userId, Long assignedById, LocalDateTime mustBeDone, Priority priority, Status status, Long householdId) {

}
