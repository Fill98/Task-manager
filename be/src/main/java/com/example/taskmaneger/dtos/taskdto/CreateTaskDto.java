package com.example.taskmaneger.dtos.taskdto;



import com.example.taskmaneger.persistence.entity.Priority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CreateTaskDto(@NotBlank(message = "Task name must not be empty.") String taskName, String description, @NotNull Long userId, @NotNull Long assignedById, LocalDateTime mustBeDone, @NotNull Priority priority, @NotNull Long householdId) {

}
