package com.shreyash.taskmanager.service;

import com.shreyash.taskmanager.dto.TaskRequestDto;
import com.shreyash.taskmanager.dto.TaskResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface TaskService {

    ResponseEntity<String> createTask(TaskRequestDto taskRequestDto);

    List<TaskResponseDto> getAllTasks();

    TaskResponseDto getTaskById(Long taskId);

    ResponseEntity<String> updateTask(Long taskId, TaskRequestDto taskRequestDto);

    ResponseEntity<String>  deleteTask(Long id);


}
