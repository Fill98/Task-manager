package com.example.taskmaneger.service;

import com.example.taskmaneger.dtos.taskdto.CreateTaskDto;
import com.example.taskmaneger.dtos.taskdto.ModifyTaskDto;
import com.example.taskmaneger.dtos.taskdto.TaskDto;
import com.example.taskmaneger.exception.NotFoundException;
import com.example.taskmaneger.persistence.entity.Household;
import com.example.taskmaneger.persistence.entity.Status;
import com.example.taskmaneger.persistence.entity.Task;
import com.example.taskmaneger.persistence.entity.User;
import com.example.taskmaneger.persistence.repository.HouseholdRepository;
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

    @Autowired
    private HouseholdRepository householdRepository;


    public TaskDto createTask(CreateTaskDto createTaskDto){
        User user = userRepository.findById(createTaskDto.userId())
                .orElseThrow(() -> new NotFoundException("User not found."));
        User assignedBy = userRepository.findById(createTaskDto.assignedById())
                .orElseThrow(() -> new NotFoundException("Assigned user not found"));
                //pridanie tasku k domacnosti
                Household household = householdRepository.findById(createTaskDto.householdId())
                        .orElseThrow(() -> new NotFoundException("Household not found."));

        Task task = new Task();
        task.setTaskName(createTaskDto.taskName());
        task.setDescription(createTaskDto.description());
        task.setUser(user);
        task.setHousehold(household);
        task.setAssignedBy(assignedBy);
        task.setMustBeDone(createTaskDto.mustBeDone());
        task.setPriority(createTaskDto.priority());
        task.setStatus(Status.TODO);

        Task saved = taskRepository.save(task);

        return toDto(saved);
    }


    public void changeStatus(Long id, Status status){
        Task task = taskRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Task not found"));
        task.setStatus(status);
        taskRepository.save(task);
    }


    public void deleteTask(Long id){

        Task task = taskRepository.findById(id).
                orElseThrow(()-> new NotFoundException("This task doesn't exist."));

        taskRepository.deleteById(id);
    }


    public TaskDto modifyTask(Long id, ModifyTaskDto newTask){

        User user = userRepository.findById(newTask.assignedTo())
                .orElseThrow(()->new NotFoundException("user not found."));

        Task task = taskRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Task not found"));
        task.setTaskName(newTask.taskName());
        task.setDescription(newTask.description());
        task.setUser(user);
        task.setMustBeDone(newTask.mustBeDone());
        task.setPriority(newTask.priority());

        Task saved = taskRepository.save(task);

        return toDto(saved);

    }



    public List<TaskDto>findAllTaskList(){
        Iterable<Task> tasks = taskRepository.findAll();
        List<TaskDto>taskDtos = new LinkedList<>();

        for(Task task : tasks){
            taskDtos.add(toDto(task)
            );
        }
        return taskDtos;
    }

    public List<TaskDto>findUserTasks(String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new NotFoundException("User not found."));
        List<TaskDto> taskDtos = new LinkedList<>();

        List<Task>tasks = user.getTaskList();

        for(Task task : tasks){
            taskDtos.add(toDto(task));
        }
        return taskDtos;


    }

    //metoda vracia ulohy domacnosti podla id
    public List<TaskDto>findHouseholdTasks(Long householdId){
        List<Task> tasks = taskRepository.findByHouseholdId(householdId);
        List<TaskDto> taskDtos = new LinkedList<>();

        for (Task task : tasks){
            taskDtos.add(toDto(task));
        }
        return taskDtos;
    }


    //return new TaskDto
    private TaskDto toDto(Task task){
        return new TaskDto(
                task.getId(),
                task.getTaskName(),
                task.getDescription(),
                task.getUser().getUsername(),
                task.getAssignedBy().getUsername(),
                task.getMustBeDone(),
                task.getPriority(),
                task.getStatus()
        );
    }

}
