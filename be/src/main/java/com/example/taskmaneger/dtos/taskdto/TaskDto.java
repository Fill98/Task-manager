package com.example.taskmaneger.dtos.taskdto;

import com.example.taskmaneger.persistence.entity.Priority;
import com.example.taskmaneger.persistence.entity.Status;

import java.time.LocalDateTime;

public record TaskDto(Long id, String taskName, String description, String assignedTo, String assignedBy, LocalDateTime mustBeDone, Priority priority, Status status) {
}
