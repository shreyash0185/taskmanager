package com.shreyash.taskmanager.service;

import com.shreyash.taskmanager.controller.TaskController;
import com.shreyash.taskmanager.dto.TaskRequestDto;
import com.shreyash.taskmanager.dto.TaskResponseDto;
import com.shreyash.taskmanager.entity.TaskEntity;
import com.shreyash.taskmanager.enums.TaskPriority;
import com.shreyash.taskmanager.enums.TaskStatus;
import com.shreyash.taskmanager.impl.TaskServiceImpl;
import com.shreyash.taskmanager.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@WebMvcTest(TaskService.class)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private TaskServiceImpl taskService;

    private TaskRequestDto requestDto;
    private TaskEntity taskEntity;
    private TaskResponseDto responseDto;

    @BeforeEach
    void setup() {
        requestDto = new TaskRequestDto();
        requestDto.setTitle("Unit Test Task");
        requestDto.setDescription("Testing");
        requestDto.setPriority(TaskPriority.HIGH);
        requestDto.setStatus(TaskStatus.PENDING);
        requestDto.setDueDate(LocalDate.now().plusDays(7));

        taskEntity = new TaskEntity();
        taskEntity.setId(1L);
        taskEntity.setTitle(requestDto.getTitle());
        taskEntity.setDescription(requestDto.getDescription());
        taskEntity.setPriority(requestDto.getPriority());
        taskEntity.setStatus(requestDto.getStatus());
        taskEntity.setDueDate(requestDto.getDueDate());

        responseDto = new TaskResponseDto();
        responseDto.setId(taskEntity.getId());
        responseDto.setTitle(taskEntity.getTitle());
        responseDto.setDescription(taskEntity.getDescription());
        responseDto.setPriority(taskEntity.getPriority().name());
        responseDto.setStatus(taskEntity.getStatus().name());
        responseDto.setDueDate(taskEntity.getDueDate().toString());
    }

    @Test
    void testCreateTask_Success() {
        when(modelMapper.map(requestDto, TaskEntity.class)).thenReturn(taskEntity);
        when(taskRepository.save(any(TaskEntity.class))).thenReturn(taskEntity);

        ResponseEntity<String> response = taskService.createTask(requestDto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Task created successfully!", response.getBody());
        verify(taskRepository, times(1)).save(any(TaskEntity.class));
    }

    @Test
    void testUpdateTask_Success() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(taskEntity));
        when(taskRepository.save(any(TaskEntity.class))).thenReturn(taskEntity);
        when(modelMapper.map(requestDto, TaskEntity.class)).thenReturn(taskEntity);

        ResponseEntity<String> response = taskService.updateTask(1L, requestDto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Task updated successfully!", response.getBody());
        verify(taskRepository).save(any(TaskEntity.class));
    }

    @Test
    void testUpdateTask_NotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<String> response = taskService.updateTask(1L, requestDto);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Task not found!", response.getBody());
        verify(taskRepository, never()).save(any(TaskEntity.class));
    }

    @Test
    void testDeleteTask_Success() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(taskEntity));
        doNothing().when(taskRepository).delete(taskEntity);

        ResponseEntity<String> response = taskService.deleteTask(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Task deleted successfully!", response.getBody());
        verify(taskRepository).delete(taskEntity);
    }

    @Test
    void testDeleteTask_NotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<String> response = taskService.deleteTask(1L);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Task not found!", response.getBody());
    }

    @Test
    void testGetTaskById_Success() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(taskEntity));
        when(modelMapper.map(taskEntity, TaskResponseDto.class)).thenReturn(responseDto);

        TaskResponseDto result = taskService.getTaskById(1L);

        assertNotNull(result);
        assertEquals(taskEntity.getId(), result.getId());
        assertEquals(taskEntity.getTitle(), result.getTitle());
    }

    @Test
    void testGetTaskById_NotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        TaskResponseDto result = taskService.getTaskById(1L);

        assertNull(result);
    }

    @Test
    void testGetAllTasks_WithResults() {
        TaskEntity task2 = new TaskEntity();
        task2.setId(2L);
        task2.setTitle("Another Task");
        task2.setDescription("Second one");
        task2.setPriority(TaskPriority.MEDIUM);
        task2.setStatus(TaskStatus.COMPLETED);
        task2.setDueDate(LocalDate.now().plusDays(10));

        TaskResponseDto response2 = new TaskResponseDto();
        response2.setId(task2.getId());
        response2.setTitle(task2.getTitle());
        response2.setDescription(task2.getDescription());
        response2.setPriority(task2.getPriority().name());
        response2.setStatus(task2.getStatus().name());
        response2.setDueDate(task2.getDueDate().toString());

        when(taskRepository.findAll()).thenReturn(Arrays.asList(taskEntity, task2));
        when(modelMapper.map(taskEntity, TaskResponseDto.class)).thenReturn(responseDto);
        when(modelMapper.map(task2, TaskResponseDto.class)).thenReturn(response2);

        List<TaskResponseDto> result = taskService.getAllTasks();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testGetAllTasks_EmptyList() {
        when(taskRepository.findAll()).thenReturn(Collections.emptyList());

        List<TaskResponseDto> result = taskService.getAllTasks();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}