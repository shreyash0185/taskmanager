package com.shreyash.taskmanager.impl;

import com.shreyash.taskmanager.dto.TaskRequestDto;
import com.shreyash.taskmanager.dto.TaskResponseDto;
import com.shreyash.taskmanager.exception.TaskNotFoundException;
import com.shreyash.taskmanager.mapper.TaskMapper;
import com.shreyash.taskmanager.repository.TaskRepository;
import com.shreyash.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;
import com.shreyash.taskmanager.entity.TaskEntity;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    TaskMapper taskMapper;


    @Override
    public ResponseEntity<String> createTask(TaskRequestDto taskRequestDto) {
        TaskEntity taskEntity = taskMapper.toEntity(taskRequestDto);
        taskRepository.save(taskEntity);
        return ResponseEntity.ok("Task created successfully");
    }

    @Override
    public List<TaskResponseDto> getAllTasks() {
        List<TaskEntity> taskEntities = taskRepository.findAll();
        return taskMapper.toDtoList(taskEntities);
    }

    @Override
    public TaskResponseDto getTaskById(Long taskId) {
        TaskEntity taskEntity = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + taskId));
        return taskMapper.toDto(taskEntity);
    }

    @Override
    public ResponseEntity<String> updateTask(Long taskId, TaskRequestDto taskRequestDto) {
        TaskEntity taskEntity = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + taskId));

        // Update the task entity with new values
        taskEntity.setTitle(taskRequestDto.getTitle());
        taskEntity.setDescription(taskRequestDto.getDescription());
        taskEntity.setDueDate(taskRequestDto.getDueDate());
        taskEntity.setStatus(taskRequestDto.getStatus());

        taskRepository.save(taskEntity);
        return ResponseEntity.ok("Task updated successfully");

    }

    @Override
    public ResponseEntity<String> deleteTask(Long id) {
        TaskEntity taskEntity = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));
        taskRepository.delete(taskEntity);
        return ResponseEntity.ok("Task deleted successfully");
    }


}
