package com.example.taskmaneger.dtos.taskdto;

import com.example.taskmaneger.persistence.entity.Priority;
import com.example.taskmaneger.persistence.entity.Status;

import java.time.LocalDateTime;

public record ModifyTaskDto(String taskName, String description, Long assignedTo,  LocalDateTime mustBeDone, Priority priority, Status status) {
}
