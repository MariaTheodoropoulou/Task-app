package gr.aueb.cf.taskapp.mapper;

import gr.aueb.cf.taskapp.dto.*;
import gr.aueb.cf.taskapp.model.Task;
import gr.aueb.cf.taskapp.model.User;
import org.springframework.stereotype.Component;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class Mapper {

    //task->readonly
    public TaskReadOnlyDTO mapToTaskReadOnlyDTO(Task task) {
        TaskReadOnlyDTO dto= new TaskReadOnlyDTO();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setStatus(task.getStatus());
        dto.setDueDate(task.getDueDate());
        dto.setCreatedAt(task.getCreatedAt());
        dto.setUpdated(task.getUpdated());

        return dto;
    }

    //insert->task
    public Task mapToTaskEntity (TaskInsertDTO taskInsertDTO) {
        Task task= new Task();
        task.setTitle(taskInsertDTO.getTitle());
        task.setDescription(taskInsertDTO.getDescription());
        task.setStatus(taskInsertDTO.getStatus());
        task.setDueDate(taskInsertDTO.getDueDate());
        return task;
    }

    //update->task
    public Task mapToTaskEntity (TaskUpdateDTO taskUpdateDTO) {
        Task task= new Task();
        task.setId(taskUpdateDTO.getId());
        task.setTitle(taskUpdateDTO.getTitle());
        task.setDescription(taskUpdateDTO.getDescription());
        task.setStatus(taskUpdateDTO.getStatus());
        task.setDueDate(taskUpdateDTO.getDueDate());
        return task;
    }

    public UserReadOnlyDTO mapToUserReadOnlyDTO(User user) {
        UserReadOnlyDTO dto= new UserReadOnlyDTO();
        dto.setFirstname(user.getFirstname());
        dto.setLastname(user.getLastname());
        dto.setRole(user.getRole());

        Set<TaskReadOnlyDTO> taskDTOs = user.getTasks().stream()
                .map(this::mapToTaskReadOnlyDTO)
                .collect(Collectors.toSet());

        dto.setTasks(taskDTOs);
        return dto;
    }
    public User mapToUserEntity (UserInsertDTO userInsertDTO) {
        User user= new User();
        user.setUsername(userInsertDTO.getUsername());
        user.setPassword(userInsertDTO.getPassword());
        user.setFirstname(userInsertDTO.getFirstname());
        user.setLastname(userInsertDTO.getLastname());
        user.setRole(userInsertDTO.getRole());
        return user;
    }
    public User mapToUserEntity (UserUpdateDTO userUpdateDTO) {
        User user= new User();
        user.setUsername(userUpdateDTO.getUsername());
        user.setPassword(userUpdateDTO.getPassword());
        user.setFirstname(userUpdateDTO.getFirstname());
        user.setLastname(userUpdateDTO.getLastname());
        user.setRole(userUpdateDTO.getRole());

        var tasks = user.getTasks().stream().collect(Collectors.toSet());
        user.setTasks(tasks);
        return user;
    }
}
