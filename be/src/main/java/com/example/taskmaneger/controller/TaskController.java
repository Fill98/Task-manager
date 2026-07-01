package com.example.taskmaneger.controller;

import com.example.taskmaneger.dtos.taskdto.CreateTaskDto;
import com.example.taskmaneger.dtos.taskdto.ModifyTaskDto;
import com.example.taskmaneger.dtos.taskdto.TaskDto;
import com.example.taskmaneger.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {

    @Autowired
    TaskService taskService;

    @PostMapping("/api/task")
    public TaskDto createTask(@RequestBody CreateTaskDto createTaskDto){
        return taskService.createTask(createTaskDto);
    }

    @PutMapping("/api/task/{id}")
    public TaskDto modifyTask(@PathVariable Long id, @RequestBody ModifyTaskDto modifyTaskDto){
        return taskService.modifyTask(id,modifyTaskDto);
    }

    @DeleteMapping("/api/task/{id}")
    public void deleteTask(@PathVariable Long id){
        taskService.deleteTask(id);
    }

    @GetMapping("/api/task")
    public List<TaskDto> findAllTasks(){
        return taskService.findAllTaskList();
    }

    @GetMapping("/api/task/{username}")
    public List<TaskDto> findUserTasks(@PathVariable String username){
        return taskService.findUserTasks(username);
    }
}
