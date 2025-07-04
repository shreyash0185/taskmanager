package com.shreyash.taskmanager.repository;

import com.shreyash.taskmanager.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    List<Task> findByStatus(String status);
    List<Task> findByPriority(String priority);

}
