package gr.aueb.cf.taskapp.controllers;

import gr.aueb.cf.taskapp.core.exceptions.InvalidTaskException;
import gr.aueb.cf.taskapp.core.exceptions.TaskNotFoundException;
import gr.aueb.cf.taskapp.dto.TaskInsertDTO;
import gr.aueb.cf.taskapp.dto.TaskReadOnlyDTO;
import gr.aueb.cf.taskapp.dto.TaskUpdateDTO;
import gr.aueb.cf.taskapp.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/task/save")
    public ResponseEntity<TaskReadOnlyDTO> saveTask(
            @Valid
            @RequestBody TaskInsertDTO taskInsertDTO, BindingResult bindingResult) throws InvalidTaskException {

        if (bindingResult.hasErrors()) {
           throw new InvalidTaskException("Task not found");
        }

        TaskReadOnlyDTO taskReadOnlyDTO = taskService.createTask(taskInsertDTO);
        return new ResponseEntity<>(taskReadOnlyDTO, HttpStatus.OK);
    }

    @GetMapping("/task/get/{id}")
    public ResponseEntity<TaskReadOnlyDTO> getTask(@PathVariable long id) throws TaskNotFoundException{
        TaskReadOnlyDTO taskReadOnlyDTO = taskService.getTaskById(id);
        return new ResponseEntity<>(taskReadOnlyDTO, HttpStatus.OK);
    }

    @PatchMapping("/task/update/{id}")
    public ResponseEntity<TaskReadOnlyDTO> updateTask(
            @Valid
            @PathVariable long id,
            @RequestBody TaskUpdateDTO taskUpdateDTO
            , BindingResult bindingResult) throws TaskNotFoundException{

        if (bindingResult.hasErrors()) {
            throw new TaskNotFoundException(id);
        }

        TaskReadOnlyDTO taskReadOnlyDTO = taskService.updateTask(id, taskUpdateDTO);
        return new ResponseEntity<>(taskReadOnlyDTO, HttpStatus.OK);
    }
    @DeleteMapping("/task/delete/{id}")
    public ResponseEntity<TaskReadOnlyDTO> deleteTask(@PathVariable long id) throws TaskNotFoundException{
        TaskReadOnlyDTO taskReadOnlyDTO = taskService.deleteTask(id);
        return new ResponseEntity<>(taskReadOnlyDTO, HttpStatus.OK);
    }

    @GetMapping("/task/all")
    public ResponseEntity<List<TaskReadOnlyDTO>> getAllTasks() throws InvalidTaskException {
        List<TaskReadOnlyDTO> taskReadOnlyDTOList = taskService.getAllTasks();
        return new ResponseEntity<>(taskReadOnlyDTOList, HttpStatus.OK);
    }

    @GetMapping("/task/change/{id}")
    public ResponseEntity<TaskReadOnlyDTO> changeTask(@PathVariable long id) throws TaskNotFoundException {
        TaskReadOnlyDTO taskReadOnlyDTO = taskService.changeStatus(id);
        return new ResponseEntity<>(taskReadOnlyDTO, HttpStatus.OK);
    }
}

