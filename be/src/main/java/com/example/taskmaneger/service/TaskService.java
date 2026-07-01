package com.example.taskmaneger.service;

import com.example.taskmaneger.dtos.taskdto.CreateTaskDto;
import com.example.taskmaneger.dtos.taskdto.ModifyTaskDto;
import com.example.taskmaneger.dtos.taskdto.TaskDto;
import com.example.taskmaneger.persistence.entity.Status;
import com.example.taskmaneger.persistence.entity.Task;
import com.example.taskmaneger.persistence.entity.User;
import com.example.taskmaneger.persistence.repository.TaskRepository;
import com.example.taskmaneger.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    UserRepository userRepository;


    public TaskDto createTask(CreateTaskDto createTaskDto){
        User user = userRepository.findById(createTaskDto.userId())
                .orElseThrow(() -> new RuntimeException("User not found."));

        User assignedBy = userRepository.findById(createTaskDto.assignedById())
                .orElseThrow(() -> new RuntimeException("Assigned user not found"));

        Task task = new Task();
        task.setTaskName(createTaskDto.taskName());
        task.setDescription(createTaskDto.description());
        task.setUser(user);
        task.setAssignedBy(assignedBy);
        task.setMustBeDone(createTaskDto.mustBeDone());
        task.setPriority(createTaskDto.priority());
        task.setStatus(Status.TODO);

        Task saved = taskRepository.save(task);

        return new TaskDto(
                saved.getId(),
                saved.getTaskName(),
                saved.getDescription(),
                saved.getUser().getUsername(),
                saved.getAssignedBy().getUsername(),
                saved.getMustBeDone(),
                saved.getPriority(),
                saved.getStatus()
        );
    }

    //todo markAsCompletedTask


    public void deleteTask(Long id){
        taskRepository.deleteById(id);
    }


    public TaskDto modifyTask(Long id, ModifyTaskDto newTask){

        User user = userRepository.findById(newTask.assignedTo())
                .orElseThrow(()->new RuntimeException("user not found."));

        Task task = taskRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Task not found"));
        task.setTaskName(newTask.taskName());
        task.setDescription(newTask.description());
        task.setUser(user);
        task.setMustBeDone(newTask.mustBeDone());
        task.setPriority(newTask.priority());
        task.setStatus(newTask.status());

        Task saved = taskRepository.save(task);

        return new TaskDto(
                saved.getId(),
                saved.getTaskName(),
                saved.getDescription(),
                saved.getUser().getUsername(),
                saved.getAssignedBy().getUsername(),
                saved.getMustBeDone(),
                saved.getPriority(),
                saved.getStatus()
        );

    }



    public List<TaskDto>findAllTaskList(){
        Iterable<Task> tasks = taskRepository.findAll();
        List<TaskDto>taskDtos = new LinkedList<>();

        for(Task task : tasks){
            taskDtos.add(new TaskDto(
                    task.getId(),
                    task.getTaskName(),
                    task.getDescription(),
                    task.getUser().getUsername(),
                    task.getAssignedBy().getUsername(),
                    task.getMustBeDone(),
                    task.getPriority(),
                    task.getStatus()
            ));
        }
        return taskDtos;
    }

    public List<TaskDto>findUserTasks(String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new RuntimeException("User not found."));
        List<TaskDto> taskDtos = new LinkedList<>();

        List<Task>tasks = user.getTaskList();

        for(Task task : tasks){
            taskDtos.add(new TaskDto(
                    task.getId(),
                    task.getTaskName(),
                    task.getDescription(),
                    task.getUser().getUsername(),
                    task.getAssignedBy().getUsername(),
                    task.getMustBeDone(),
                    task.getPriority(),
                    task.getStatus()
            ));

        }
        return taskDtos;


    }

}
