package gr.aueb.cf.taskapp.service;

import gr.aueb.cf.taskapp.core.enums.Status;
import gr.aueb.cf.taskapp.core.exceptions.TaskNotFoundException;
import gr.aueb.cf.taskapp.core.exceptions.UserNotFoundException;
import gr.aueb.cf.taskapp.model.Task;
import gr.aueb.cf.taskapp.model.User;
import gr.aueb.cf.taskapp.repository.TaskRepository;
import gr.aueb.cf.taskapp.core.exceptions.InvalidTaskException;
import gr.aueb.cf.taskapp.dto.TaskInsertDTO;
import gr.aueb.cf.taskapp.dto.TaskReadOnlyDTO;
import gr.aueb.cf.taskapp.dto.TaskUpdateDTO;
import gr.aueb.cf.taskapp.mapper.Mapper;
import gr.aueb.cf.taskapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final Mapper mapper;
    private final UserRepository userRepository;


    public TaskReadOnlyDTO createTask(TaskInsertDTO taskInsertDTO) throws InvalidTaskException {
        if(taskRepository.findByTitle(taskInsertDTO.getTitle()).isPresent()) {
            throw new InvalidTaskException("Task title already exists");
        }
        Task savedTask = taskRepository.save(mapper.mapToTaskEntity(taskInsertDTO));
        savedTask.setUser(new User());
        savedTask.getUser().setId(0L);
        TaskReadOnlyDTO taskToReturn = mapper.mapToTaskReadOnlyDTO(savedTask);
        taskToReturn.setUserId(savedTask.getUser().getId());
        return taskToReturn;
    };


    public TaskReadOnlyDTO getTaskById(Long id) throws TaskNotFoundException {
       Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
       TaskReadOnlyDTO taskToReturn = mapper.mapToTaskReadOnlyDTO(task);
       if (task.getUser() == null) {
           User user = new User();
           user.setId(0L);
           taskToReturn.setUserId(user.getId());
       } else {
           taskToReturn.setUserId(task.getUser().getId());
       }
            return taskToReturn;
    };

    public TaskReadOnlyDTO updateTask(Long id, TaskUpdateDTO taskUpdateDTO) throws TaskNotFoundException{
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
        task.setTitle(taskUpdateDTO.getTitle());
        task.setDescription(taskUpdateDTO.getDescription());
        task.setStatus(taskUpdateDTO.getStatus());
        task.setDueDate(taskUpdateDTO.getDueDate());
        return mapper.mapToTaskReadOnlyDTO(taskRepository.save(task));
    };


    public TaskReadOnlyDTO deleteTask(Long id) throws TaskNotFoundException {
        if(taskRepository.findById(id).isPresent()) {
            Task task = taskRepository.findById(id).get();
            taskRepository.delete(task);
            return mapper.mapToTaskReadOnlyDTO(task);
        } else {
            throw new TaskNotFoundException(id);
        }
    };
    public List<TaskReadOnlyDTO> getAllTasks() throws InvalidTaskException, TaskNotFoundException, UserNotFoundException {
        List<Task> tasks = taskRepository.findAll();
        if(tasks.isEmpty()) {
            throw new InvalidTaskException("No tasks found");
        }
        List<TaskReadOnlyDTO> tasksList = new ArrayList<>();
        for (Task task : tasks) {
            TaskReadOnlyDTO taskToReturn = mapper.mapToTaskReadOnlyDTO(task);
            if (task.getUser() == null) {
                User user = new User();
                user.setId(0L);
                taskToReturn.setUserId(user.getId());
                tasksList.add(taskToReturn);
            } else {
//                Task dbTask = taskRepository.findById(task.getId()).orElseThrow(() -> new UserNotFoundException("User not found"));
                taskToReturn.setUserId(task.getUser().getId());
                tasksList.add(taskToReturn);
            }
        }
        return tasksList;
    };

    public TaskReadOnlyDTO changeStatus(Long id) throws TaskNotFoundException {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
        task.getStatus();
        task.setStatus(task.getStatus() == Status.IN_PROGRESS ? Status.COMPLETED : Status.IN_PROGRESS);
        return mapper.mapToTaskReadOnlyDTO(taskRepository.save(task));
    }

    public TaskReadOnlyDTO changeUser(Long userId, Long taskId) throws TaskNotFoundException {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));
        User user = userRepository.findById(userId).orElseThrow(() -> new TaskNotFoundException(userId));
        task.setUser(user);
        return mapper.mapToTaskReadOnlyDTO(taskRepository.save(task));
    }
}
