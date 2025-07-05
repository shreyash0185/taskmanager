package com.shreyash.taskmanager.repository;

import com.shreyash.taskmanager.entity.TaskEntity;
import com.shreyash.taskmanager.enums.TaskPriority;
import com.shreyash.taskmanager.enums.TaskStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void testSaveAndFindById() {
        TaskEntity task = new TaskEntity();
        task.setTitle("Test Task");
        task.setDescription("JPA Test");
        task.setPriority(TaskPriority.MEDIUM);
        task.setStatus(TaskStatus.PENDING);
        task.setDueDate(LocalDate.now().plusDays(5));

        TaskEntity savedTask = taskRepository.save(task);

        assertNotNull(savedTask.getId());
        assertEquals("Test Task", savedTask.getTitle());
    }

    @Test
    void testFindByStatus() {
        TaskEntity task1 = new TaskEntity();
        task1.setTitle("Pending Task");
        task1.setStatus(TaskStatus.PENDING);
        task1.setDueDate(LocalDate.now().plusDays(2));
        taskRepository.save(task1);

        TaskEntity task2 = new TaskEntity();
        task2.setTitle("Completed Task");
        task2.setStatus(TaskStatus.COMPLETED);
        task2.setDueDate(LocalDate.now().plusDays(3));
        taskRepository.save(task2);

        List<TaskEntity> pendingTasks = taskRepository.findByStatus(TaskStatus.PENDING);
        assertEquals(1, pendingTasks.size());
        assertEquals("Pending Task", pendingTasks.get(0).getTitle());
    }

}
