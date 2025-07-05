package com.shreyash.taskmanager.repository;

import com.shreyash.taskmanager.entity.TaskEntity;
import com.shreyash.taskmanager.enums.TaskPriority;
import com.shreyash.taskmanager.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    List<TaskEntity> findByDueDate(LocalDate dueDate);

    List<TaskEntity> findByStatus(TaskStatus status);

    List<TaskEntity> findByPriority(TaskPriority priority);

    List<TaskEntity> findByTitleContainingIgnoreCase(String title);

}
