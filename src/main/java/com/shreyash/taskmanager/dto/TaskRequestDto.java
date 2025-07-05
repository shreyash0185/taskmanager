package com.shreyash.taskmanager.dto;

import com.shreyash.taskmanager.enums.TaskPriority;
import com.shreyash.taskmanager.enums.TaskStatus;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequestDto {

    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Status is required")
    private TaskStatus status;

    @NotNull(message = "Priority is required")
    private TaskPriority priority;

    @FutureOrPresent(message = "Due date must be today or in the future")
    private LocalDate dueDate;

    public @NotBlank(message = "Title is required") @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters") String getTitle() {
        return title;
    }

    public void setTitle(@NotBlank(message = "Title is required") @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters") String title) {
        this.title = title;
    }

    public @NotBlank(message = "Description is required") String getDescription() {
        return description;
    }

    public void setDescription(@NotBlank(message = "Description is required") String description) {
        this.description = description;
    }

    public @NotNull(message = "Status is required") TaskStatus getStatus() {
        return status;
    }

    public void setStatus(@NotNull(message = "Status is required") TaskStatus status) {
        this.status = status;
    }

    public @NotNull(message = "Priority is required") TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(@NotNull(message = "Priority is required") TaskPriority priority) {
        this.priority = priority;
    }

    public @FutureOrPresent(message = "Due date must be today or in the future") LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(@FutureOrPresent(message = "Due date must be today or in the future") LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}
