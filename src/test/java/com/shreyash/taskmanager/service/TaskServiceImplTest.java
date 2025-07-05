package com.shreyash.taskmanager.service;


import com.shreyash.taskmanager.dto.TaskRequestDto;
import com.shreyash.taskmanager.dto.TaskResponseDto;
import com.shreyash.taskmanager.entity.TaskEntity;
import com.shreyash.taskmanager.enums.TaskPriority;
import com.shreyash.taskmanager.enums.TaskStatus;
import com.shreyash.taskmanager.exception.TaskNotFoundException;
import com.shreyash.taskmanager.impl.TaskServiceImpl;
import com.shreyash.taskmanager.mapper.TaskMapper;
import com.shreyash.taskmanager.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTest {

    @InjectMocks
    private TaskServiceImpl taskService;

    @Mock
    private TaskMapper taskMapper;

    @Mock
    private TaskRepository taskRepository;

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

        when(taskMapper.toEntity(requestDto)).thenReturn(taskEntity);
        when(taskRepository.save(any(TaskEntity.class))).thenReturn(taskEntity);


        ResponseEntity<String> response = taskService.createTask(requestDto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Task created successfully", response.getBody());
        verify(taskRepository, times(1)).save(any(TaskEntity.class));
    }

    @Test
    void testUpdateTask_Success() {
        TaskRequestDto requestDto = new TaskRequestDto();
        requestDto.setTitle("Updated Title");
        requestDto.setDescription("Updated Description");
        requestDto.setDueDate(LocalDate.now().plusDays(5));
        requestDto.setStatus(TaskStatus.IN_PROGRESS);

        TaskEntity existingTask = new TaskEntity();
        existingTask.setId(1L);
        existingTask.setTitle("Old Title");
        existingTask.setDescription("Old Description");
        existingTask.setDueDate(LocalDate.now().plusDays(3));
        existingTask.setStatus(TaskStatus.PENDING);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(TaskEntity.class))).thenReturn(existingTask);

        ResponseEntity<String> response = taskService.updateTask(1L, requestDto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Task updated successfully", response.getBody());  // ✅ exact match
        verify(taskRepository).save(any(TaskEntity.class));
    }


    @Test
    void testUpdateTask_NotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        TaskNotFoundException exception = assertThrows(
                TaskNotFoundException.class,
                () -> taskService.updateTask(1L, requestDto)
        );

        assertEquals("Task not found with id: 1", exception.getMessage());
        verify(taskRepository, never()).save(any(TaskEntity.class));
    }


    @Test
    void testDeleteTask_Success() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(taskEntity));
        doNothing().when(taskRepository).delete(taskEntity);

        ResponseEntity<String> response = taskService.deleteTask(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Task deleted successfully", response.getBody());
        verify(taskRepository).delete(taskEntity);
    }

    @Test
    void testDeleteTask_NotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        TaskNotFoundException exception = assertThrows(
                TaskNotFoundException.class,
                () -> taskService.deleteTask(1L)
        );

        assertEquals("Task not found with id: 1", exception.getMessage());
    }


    @Test
    void testGetTaskById_Success() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(taskEntity));
        when(taskMapper.toDto(taskEntity)).thenReturn(responseDto);

        TaskResponseDto result = taskService.getTaskById(1L);

        assertNotNull(result);
        assertEquals(taskEntity.getId(), result.getId());
        assertEquals(taskEntity.getTitle(), result.getTitle());
    }

    @Test
    void testGetTaskById_NotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        TaskNotFoundException exception = assertThrows(
                TaskNotFoundException.class,
                () -> taskService.getTaskById(1L)
        );

        assertEquals("Task not found with id: 1", exception.getMessage());
    }


    @Test
    void testGetAllTasks_WithResults() {
        TaskEntity task1 = new TaskEntity();
        task1.setId(1L);
        task1.setTitle("Task One");
        task1.setDescription("First task");
        task1.setPriority(TaskPriority.HIGH);
        task1.setStatus(TaskStatus.PENDING);
        task1.setDueDate(LocalDate.now().plusDays(5));

        TaskEntity task2 = new TaskEntity();
        task2.setId(2L);
        task2.setTitle("Task Two");
        task2.setDescription("Second task");
        task2.setPriority(TaskPriority.MEDIUM);
        task2.setStatus(TaskStatus.COMPLETED);
        task2.setDueDate(LocalDate.now().plusDays(10));

        TaskResponseDto dto1 = new TaskResponseDto();
        dto1.setId(task1.getId());
        dto1.setTitle(task1.getTitle());
        dto1.setDescription(task1.getDescription());
        dto1.setPriority(task1.getPriority().name());
        dto1.setStatus(task1.getStatus().name());
        dto1.setDueDate(task1.getDueDate().toString());

        TaskResponseDto dto2 = new TaskResponseDto();
        dto2.setId(task2.getId());
        dto2.setTitle(task2.getTitle());
        dto2.setDescription(task2.getDescription());
        dto2.setPriority(task2.getPriority().name());
        dto2.setStatus(task2.getStatus().name());
        dto2.setDueDate(task2.getDueDate().toString());

        List<TaskEntity> entities = Arrays.asList(task1, task2);
        List<TaskResponseDto> dtos = Arrays.asList(dto1, dto2);

        when(taskRepository.findAll()).thenReturn(entities);
        when(taskMapper.toDtoList(entities)).thenReturn(dtos); // ✅ This is the key fix

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