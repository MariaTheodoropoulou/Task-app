package gr.aueb.cf.taskapp.controllers;

import gr.aueb.cf.taskapp.core.exceptions.TaskNotFoundException;
import gr.aueb.cf.taskapp.core.exceptions.UserAlreadyExistsException;
import gr.aueb.cf.taskapp.core.exceptions.UserNotFoundException;
import gr.aueb.cf.taskapp.dto.UserInsertDTO;
import gr.aueb.cf.taskapp.dto.UserReadOnlyDTO;
import gr.aueb.cf.taskapp.dto.UserUpdateDTO;
import gr.aueb.cf.taskapp.mapper.Mapper;
import gr.aueb.cf.taskapp.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final Mapper mapper;

    @PostMapping("/user/save")
    public ResponseEntity<UserReadOnlyDTO> userSave(
            @Valid
            @RequestBody UserInsertDTO userInsertDTO,
            BindingResult bindingResult) throws  UserAlreadyExistsException  {

        if (bindingResult.hasErrors()) {
            throw new UserAlreadyExistsException("user already exists");
        }

        UserReadOnlyDTO userToReturn = userService.saveUser(userInsertDTO);

        return new ResponseEntity<>(userToReturn, HttpStatus.OK);
    }

    @PutMapping("/user/update/{id}")
    public ResponseEntity<UserReadOnlyDTO> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateDTO userUpdateDTO,
            BindingResult bindingResult) throws UserNotFoundException {

        if (bindingResult.hasErrors()) {
            throw new UserNotFoundException("Invalid user data provided.");
        }

        UserReadOnlyDTO updatedUser = userService.updateUser(userUpdateDTO, id);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<UserReadOnlyDTO> deleteUser(@PathVariable  Long id) throws UserNotFoundException {
        UserReadOnlyDTO userReadOnlyDTO = userService.deleteUser(id);
        return new ResponseEntity<>(userReadOnlyDTO, HttpStatus.OK);
    }

    @GetMapping("/user/all")
    public ResponseEntity<List<UserReadOnlyDTO>> getAllUsers() throws UserNotFoundException {
            return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserReadOnlyDTO> getUserById(@PathVariable Long id) throws UserNotFoundException {
        UserReadOnlyDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/user/{userId}/{taskId}")
    public ResponseEntity<UserReadOnlyDTO> appendTask(@PathVariable Long userId, @PathVariable Long taskId) throws UserNotFoundException, TaskNotFoundException {
        UserReadOnlyDTO user = userService.appendTask(userId, taskId);
        return ResponseEntity.ok(user);
    }
}
