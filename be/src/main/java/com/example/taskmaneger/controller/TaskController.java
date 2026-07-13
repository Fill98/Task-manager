package com.example.taskmaneger.controller;

import com.example.taskmaneger.dtos.taskdto.CreateTaskDto;
import com.example.taskmaneger.dtos.taskdto.ModifyTaskDto;
import com.example.taskmaneger.dtos.taskdto.TaskDto;
import com.example.taskmaneger.persistence.entity.Status;
import com.example.taskmaneger.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {

    @Autowired
    TaskService taskService;

    @PostMapping("/api/task")
    public TaskDto createTask(@Valid @RequestBody CreateTaskDto createTaskDto){
        return taskService.createTask(createTaskDto);
    }

    //modifikovanie tasku
    @PutMapping("/api/task/{id}")
    public TaskDto modifyTask(@PathVariable Long id,@Valid @RequestBody ModifyTaskDto modifyTaskDto){
        return taskService.modifyTask(id,modifyTaskDto);
    }

    //zmena statusu tasku
    @PatchMapping("/api/task/{id}/{status}")
    public void changeStatus(@PathVariable Long id, @PathVariable Status status){
        taskService.changeStatus(id,status);
    }

    @DeleteMapping("/api/task/{id}")
    public void deleteTask(@PathVariable Long id){
        taskService.deleteTask(id);
    }

    //najdi vsetky tasky len na VYVOJ
    @GetMapping("/api/task")
    public List<TaskDto> findAllTasks(){
        return taskService.findAllTaskList();
    }

    //zorad podla casu na dokoncenie
    @GetMapping("/api/task/sort/deadline")
    public List<TaskDto> findAllSortedByDeadLine(){
        return taskService.findAllSortedByDeadLine();
    }

    //zorad podla priority
    @GetMapping("/api/task/sort/priority")
    public List<TaskDto> findAllSortedByPriority(){
        return taskService.findAllSortedByPriority();
    }

    //filtruj podla statusu taski
    @GetMapping("/api/task/filter/{status}")
    public List<TaskDto> findByStatus(@PathVariable Status status){
        return taskService.findByStatus(status);
    }

    @GetMapping("/api/task/mytasks")
    public List<TaskDto> findUserTasks(){
        return taskService.findUserTasks();
    }

    //metoda vracia ulohy domacnosti podla id
    @GetMapping("/api/task/household/{householdId}")
    public List<TaskDto> findHouseholdTasks(@PathVariable Long householdId){
        return taskService.findHouseholdTasks(householdId);
    }
}
