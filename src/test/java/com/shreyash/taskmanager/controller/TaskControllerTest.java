package com.shreyash.taskmanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shreyash.taskmanager.dto.TaskRequestDto;
import com.shreyash.taskmanager.dto.TaskResponseDto;
import com.shreyash.taskmanager.enums.TaskPriority;
import com.shreyash.taskmanager.enums.TaskStatus;
import com.shreyash.taskmanager.service.TaskService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
@AutoConfigureMockMvc
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    private TaskRequestDto sampleTaskRequest() {
        TaskRequestDto task = new TaskRequestDto();
        task.setTitle("Sample Task");
        task.setDescription("This is a sample task for testing");
        task.setPriority(TaskPriority.HIGH);
        task.setStatus(TaskStatus.PENDING);
        task.setDueDate(LocalDate.now().plusDays(5));
        return task;
    }

    private TaskResponseDto sampleTaskResponse() {
        TaskResponseDto task = new TaskResponseDto();
        task.setId(1L);
        task.setTitle("Sample Task");
        task.setDescription("This is a sample task for testing");
        task.setPriority(TaskPriority.HIGH.name());
        task.setStatus(TaskStatus.PENDING.name());
        task.setDueDate(LocalDate.now().plusDays(5).toString());
        return task;
    }

    @Test
    void testCreateTask_Success() throws Exception {
        TaskRequestDto request = sampleTaskRequest();
        Mockito.when(taskService.createTask(any(TaskRequestDto.class)))
                .thenReturn(ResponseEntity.ok("Task Created"));

        mockMvc.perform(post("/api/tasks/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) content().string("Task Created"));
    }

    @Test
    void testUpdateTask_Success() throws Exception {
        TaskRequestDto request = sampleTaskRequest();
        Mockito.when(taskService.updateTask(eq(1L), any(TaskRequestDto.class)))
                .thenReturn(ResponseEntity.ok("Task Updated"));

        mockMvc.perform(post("/api/tasks/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) content().string("Task Updated"));
    }

    @Test
    void testDeleteTask_Success() throws Exception {
        Mockito.when(taskService.deleteTask(1L))
                .thenReturn(ResponseEntity.ok("Task Deleted"));

        mockMvc.perform(post("/api/tasks/delete/1"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) content().string("Task Deleted"));
    }

    @Test
    void testGetTaskById_Found() throws Exception {
        TaskResponseDto response = sampleTaskResponse();
        Mockito.when(taskService.getTaskById(1L)).thenReturn(response);

        mockMvc.perform(post("/api/tasks/get/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Test Task"));
    }

    @Test
    void testGetTaskById_NotFound() throws Exception {
        Mockito.when(taskService.getTaskById(1L)).thenReturn(null);

        mockMvc.perform(post("/api/tasks/get/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetAllTasks_WithContent() throws Exception {
        List<TaskResponseDto> tasks = Arrays.asList(sampleTaskResponse());
        Mockito.when(taskService.getAllTasks()).thenReturn(tasks);

        mockMvc.perform(post("/api/tasks/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void testGetAllTasks_NoContent() throws Exception {
        Mockito.when(taskService.getAllTasks()).thenReturn(List.of());

        mockMvc.perform(post("/api/tasks/getAll"))
                .andExpect(status().isNoContent());
    }
}