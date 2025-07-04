package com.shreyash.taskmanager.controller;

import com.shreyash.taskmanager.dto.TaskRequestDto;
import com.shreyash.taskmanager.dto.TaskResponseDto;
import com.shreyash.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    //Create Task
   @PostMapping("/create")
    public ResponseEntity<String> createTask(@Valid @RequestBody TaskRequestDto taskRequestDto) {
       ResponseEntity<String> response = taskService.createTask(taskRequestDto);
         return response;
    }

    // Update Task
    @PostMapping("/update/{id}")
    public ResponseEntity<String> updateTask(@PathVariable("id") Long id,@Valid @RequestBody TaskRequestDto taskRequestDto) {
        ResponseEntity<String> response = taskService.updateTask(id , taskRequestDto);
        return response;
    }

    // Delete Task
    @PostMapping("/delete/{id}")
    public ResponseEntity<String>  deleteTask(@PathVariable("id") Long id) {
        ResponseEntity<String> response = taskService.deleteTask(id);
        return response;
    }

    // Get Task by ID
    @PostMapping("/get/{id}")
    public ResponseEntity<TaskResponseDto> getTaskById(@PathVariable ("id") Long id) {
        TaskResponseDto response = taskService.getTaskById(id);
        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    // Get All Tasks
    @PostMapping("/getAll")
    public ResponseEntity<List<TaskResponseDto>> getAllTasks() {
        List<TaskResponseDto> tasks = taskService.getAllTasks();
        if (tasks != null && !tasks.isEmpty()) {
            return ResponseEntity.ok(tasks);
        } else {
            return ResponseEntity.noContent().build();
        }

    }



}
