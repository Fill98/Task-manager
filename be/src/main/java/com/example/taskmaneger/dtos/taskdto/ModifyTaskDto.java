package com.example.taskmaneger.dtos.taskdto;

import com.example.taskmaneger.persistence.entity.Priority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


import java.time.LocalDateTime;

public record ModifyTaskDto(@NotBlank(message = "Task name must not be empty.") String taskName, String description, @NotNull Long assignedTo, LocalDateTime mustBeDone, @NotNull Priority priority) {
}
