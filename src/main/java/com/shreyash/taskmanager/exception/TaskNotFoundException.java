package com.shreyash.taskmanager.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;


public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException(Long id) {
        super("Task with ID " + id + " not found");
    }

    public TaskNotFoundException(String message) {
        super(message);
    }
}
