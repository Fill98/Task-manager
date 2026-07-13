package com.example.taskmaneger.service;

import com.example.taskmaneger.dtos.taskdto.*;
import com.example.taskmaneger.exception.ConflictException;
import com.example.taskmaneger.exception.ForbiddenException;
import com.example.taskmaneger.exception.NotFoundException;
import com.example.taskmaneger.persistence.entity.Household;
import com.example.taskmaneger.persistence.entity.Status;
import com.example.taskmaneger.persistence.entity.Task;
import com.example.taskmaneger.persistence.entity.User;
import com.example.taskmaneger.persistence.repository.HouseholdRepository;
import com.example.taskmaneger.persistence.repository.TaskRepository;
import com.example.taskmaneger.persistence.repository.UserRepository;
import com.example.taskmaneger.security.CurrentUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
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

    @Autowired
    private CurrentUserService currentUserService;


    public TaskDto createTask(CreateTaskDto createTaskDto){
        User user = userRepository.findById(createTaskDto.userId())
                .orElseThrow(() -> new NotFoundException("User not found."));
        User assignedBy = currentUserService.getCurrentUser();

                //pridanie tasku k domacnosti
        Household household = householdRepository.findById(createTaskDto.householdId())
                .orElseThrow(() -> new NotFoundException("Household not found."));
        //Clen domacnosti vie pridelit ulohu lne clenovy domacnsoti
        if(!household.hasMember(assignedBy)){
            throw new ForbiddenException("You are not a member of this household.");
        }
        if(!household.hasMember(user)){
            throw new ConflictException("User is not a member of this household.");
        }


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
        User user = currentUserService.getCurrentUser();

        boolean isAssignee = task.getUser().getId().equals(user.getId());

        if(!isAssignee){
            throw new ForbiddenException("You cannot change status of this task.");
        }

        task.setStatus(status);
        taskRepository.save(task);
    }

    public void deleteTask(Long id){
        Task task = taskRepository.findById(id).
                orElseThrow(()-> new NotFoundException("This task doesn't exist."));
        verifyCanModify(task,currentUserService.getCurrentUser());
        taskRepository.deleteById(id);
    }

    public TaskDto modifyTask(Long id, ModifyTaskDto newTask){

        User user = userRepository.findById(newTask.assignedTo())
                .orElseThrow(()->new NotFoundException("user not found."));

        Task task = taskRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Task not found"));

        if(task.getHousehold() == null){
            throw new ConflictException("This is not a household task.");
        }

        verifyCanModify(task,currentUserService.getCurrentUser());

        task.setTaskName(newTask.taskName());
        task.setDescription(newTask.description());
        task.setUser(user);
        task.setMustBeDone(newTask.mustBeDone());
        task.setPriority(newTask.priority());

        Task saved = taskRepository.save(task);

        return toDto(saved);

    }

    //zoradenie podla terminu vzostupne
    public List<TaskDto> findAllSortedByDeadLine() {
        User user = currentUserService.getCurrentUser();
        List<TaskDto> taskDtos = toDtoList(user.getTaskList());
        taskDtos.sort(Comparator.comparing(TaskDto::mustBeDone,Comparator.nullsLast(Comparator.naturalOrder())));

        return taskDtos;
    }

    //zoradenie podla priority (HIGH -> MEDIUM -> LOW), pri zhode podla najblizsieho terminu
    public List<TaskDto> findAllSortedByPriority(){
        User user = currentUserService.getCurrentUser();

        List<TaskDto> taskDtos = toDtoList(user.getTaskList());
        taskDtos.sort(Comparator.comparing(TaskDto::priority).reversed()
                .thenComparing(TaskDto::mustBeDone,Comparator.nullsLast(Comparator.naturalOrder())));
        return taskDtos;
    }
    //filtrovanie podla statusu a sekundarne podla terminu
    public List<TaskDto> findByStatus(Status status){
        User user = currentUserService.getCurrentUser();

        List<Task> filteredTasks = user
                .getTaskList()
                .stream() // pomocou stream sa daju retazit oepracie
                .filter(task -> task.getStatus() == status)// vyberie tie tasky ktore splnaju podmienku
                .sorted(Comparator.comparing(Task::getMustBeDone,
                        Comparator.nullsLast(Comparator.naturalOrder())))
                .toList(); // spravi naspat List<Task>

        return toDtoList(filteredTasks);
    }

    public List<TaskDto>findAllTaskList(){
        return toDtoList(taskRepository.findAll());
    }

    public List<TaskDto>findUserTasks(){

        User user = currentUserService.getCurrentUser();
        return toDtoList(user.getTaskList());
    }

    //metoda vracia ulohy domacnosti podla id
    public List<TaskDto>findHouseholdTasks(Long householdId){
        Household household = householdRepository.findById(householdId)
                .orElseThrow(()-> new NotFoundException("Household not found."));

        User currentUser = currentUserService.getCurrentUser();

        boolean hasAcces = household.hasMember(currentUser);

        if(!hasAcces){
            throw new ForbiddenException("Member is not in Household.");
        }

        return toDtoList(taskRepository.findByHouseholdId(householdId));
    }

    /*
    Perosnalne tasky
     */

    public TaskDto createPersonalTask(CreatePersonalTaskDto createPersonalTaskDto){
        Task task = new Task();
        User user = currentUserService.getCurrentUser();

        task.setTaskName(createPersonalTaskDto.taskName());
        task.setDescription(createPersonalTaskDto.description());
        task.setUser(user);
        task.setAssignedBy(user);
        task.setHousehold(null);
        task.setMustBeDone(createPersonalTaskDto.mustBeDone());
        task.setPriority(createPersonalTaskDto.priority());
        task.setStatus(Status.TODO);

        return toDto(taskRepository.save(task));
    }

    public TaskDto modifyPersonalTask(Long id,ModifyPersonalTaskDto taskDto){
        Task task = taskRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Task not found."));

        if(task.getHousehold() != null){
            throw new ConflictException("This is not a personal task.");
        }
        verifyCanModify(task, currentUserService.getCurrentUser());

        task.setTaskName(taskDto.taskName());
        task.setDescription(taskDto.description());
        task.setMustBeDone(taskDto.mustBeDone());
        task.setPriority(taskDto.priority());

        return toDto(taskRepository.save(task));

    }

/*
    POMOCNE METODY
 */

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

    private List<TaskDto> toDtoList(Iterable<Task> tasks){
        List<TaskDto> taskDtos = new LinkedList<>();
        for (Task task : tasks){
            taskDtos.add(toDto(task)
            );
        }
        return taskDtos;
    }

    private void verifyCanModify(Task task, User user){
        boolean isCreator = user.getId().equals(task.getAssignedBy().getId());

        if(!isCreator){
            throw new ForbiddenException("You are not allowed to modify this task.");
        }
    }



}
