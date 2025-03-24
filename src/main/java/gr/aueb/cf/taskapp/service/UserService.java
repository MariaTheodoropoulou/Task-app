package gr.aueb.cf.taskapp.service;

import gr.aueb.cf.taskapp.core.exceptions.TaskNotFoundException;
import gr.aueb.cf.taskapp.core.exceptions.UserAlreadyExistsException;
import gr.aueb.cf.taskapp.core.exceptions.UserNotFoundException;
import gr.aueb.cf.taskapp.dto.UserInsertDTO;
import gr.aueb.cf.taskapp.dto.UserReadOnlyDTO;
import gr.aueb.cf.taskapp.dto.UserUpdateDTO;
import gr.aueb.cf.taskapp.mapper.Mapper;
import gr.aueb.cf.taskapp.model.Task;
import gr.aueb.cf.taskapp.model.User;
import gr.aueb.cf.taskapp.repository.TaskRepository;
import gr.aueb.cf.taskapp.repository.UserRepository;
import gr.aueb.cf.taskapp.security.SecurityConfiguration;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final SecurityConfiguration securityConfiguration;
    private final PasswordEncoder passwordEncoder;
    private final Mapper mapper;
    private final TaskRepository taskRepository;
    private final TaskService taskService;

    public UserReadOnlyDTO saveUser(UserInsertDTO userInsertDTO) throws UserAlreadyExistsException {
        // Έλεγχος αν υπάρχει χρήστης με ίδιο username ΚΑΙ lastname
        Optional<User> existingUser = userRepository
                .findByUsernameAndLastname(userInsertDTO.getUsername(), userInsertDTO.getLastname());

        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException("User already exists with username: "
                    + userInsertDTO.getUsername()
                    + " and lastname: "
                    + userInsertDTO.getLastname());
        }

        // Δημιουργία νέου χρήστη και αποθήκευση
        User user = mapper.mapToUserEntity(userInsertDTO);
        user.setPassword(securityConfiguration.passwordEncoder().encode(userInsertDTO.getPassword()));

        User savedUser = userRepository.save(user);

        // Επιστροφή DTO
        return mapper.mapToUserReadOnlyDTO(savedUser);
    }

    public UserReadOnlyDTO updateUser(UserUpdateDTO userUpdateDTO, Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        user.setUsername(userUpdateDTO.getUsername());
        user.setPassword((passwordEncoder.encode(userUpdateDTO.getPassword())));
        user.setFirstname(userUpdateDTO.getFirstname());
        user.setLastname(userUpdateDTO.getLastname());
        user.setRole(userUpdateDTO.getRole());
        User savedUser = userRepository.save(user);
        return mapper.mapToUserReadOnlyDTO(savedUser);
    }

    public UserReadOnlyDTO deleteUser(Long id) throws UserNotFoundException {
        if (userRepository.findById(id).isPresent()) {
            User user = userRepository.findById(id).get();
            userRepository.delete(user);
            return mapper.mapToUserReadOnlyDTO(user);
        } else {
            throw new UserNotFoundException("User not found with id: " + id);
        }
    }

    public List<UserReadOnlyDTO> getAllUsers() throws UserNotFoundException {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new UserNotFoundException("No users found in the database");
        }
        return users.stream()
                .map(mapper::mapToUserReadOnlyDTO)
                .collect(Collectors.toList());
    }

    public UserReadOnlyDTO getUserById(Long id) throws UserNotFoundException {
        if (userRepository.findById(id).isPresent()) {
            User user = userRepository.findById(id).get();
            return mapper.mapToUserReadOnlyDTO(user);
        } else {
            throw new UserNotFoundException("User not found with id: " + id);
        }
    }
    public UserReadOnlyDTO appendTask(Long userId, Long taskId) throws UserNotFoundException, TaskNotFoundException {
        User user = new User();
        if (userRepository.findById(userId).isEmpty()) {
            throw new UserNotFoundException("User not found with id: " + userId);
        } else {
            user = userRepository.findById(userId).get();
            Set<Task> tasks = user.getTasks();
            if (taskRepository.findById(taskId).isEmpty()) {
                throw new TaskNotFoundException(taskId);
            } else {
                Task task = taskRepository.findById(taskId).get();
                task.setUser(user);
                tasks.add(task);
                taskService.changeUser(userId, taskId);
                user.setTasks(tasks);
            }

        }
        UserReadOnlyDTO userReadOnlyDTO = mapper.mapToUserReadOnlyDTO(user);
        var tasksReadOnlyDTO = userReadOnlyDTO.getTasks();
        for (var task : tasksReadOnlyDTO) {
            task.setUserId(userId);
        }
        userReadOnlyDTO.setTasks(tasksReadOnlyDTO);
        return userReadOnlyDTO;
    }

}
