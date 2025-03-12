package gr.aueb.cf.taskapp.service;

import gr.aueb.cf.taskapp.core.enums.Status;
import gr.aueb.cf.taskapp.core.exceptions.TaskNotFoundException;
import gr.aueb.cf.taskapp.model.Task;
import gr.aueb.cf.taskapp.repository.TaskRepository;
import gr.aueb.cf.taskapp.core.exceptions.InvalidTaskException;
import gr.aueb.cf.taskapp.dto.TaskInsertDTO;
import gr.aueb.cf.taskapp.dto.TaskReadOnlyDTO;
import gr.aueb.cf.taskapp.dto.TaskUpdateDTO;
import gr.aueb.cf.taskapp.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final Mapper mapper;


    public TaskReadOnlyDTO createTask(TaskInsertDTO taskInsertDTO) throws InvalidTaskException {
        if(taskRepository.findByTitle(taskInsertDTO.getTitle()).isPresent()) {
            throw new InvalidTaskException("Task title already exists");
        }

        Task task = mapper.mapToTaskEntity(taskInsertDTO);
        Task savedTask = taskRepository.save(task);
        return mapper.mapToTaskReadOnlyDTO(savedTask);
    };


    public TaskReadOnlyDTO getTaskById(Long id) throws TaskNotFoundException {
        if(taskRepository.findById(id).isEmpty()) {
            throw new TaskNotFoundException(id);
        } else {
            return mapper.mapToTaskReadOnlyDTO(taskRepository.findById(id).get());
        }

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
    public List<TaskReadOnlyDTO> getAllTasks() throws InvalidTaskException {
        List<Task> tasks = taskRepository.findAll();

        if(tasks.isEmpty()) {
            throw new InvalidTaskException("No tasks found");
        }
        return tasks.stream()
                .map(mapper::mapToTaskReadOnlyDTO)
                .collect(Collectors.toList());
    };

    public TaskReadOnlyDTO changeStatus(Long id) throws TaskNotFoundException {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
        task.getStatus();
        task.setStatus(task.getStatus() == Status.IN_PROGRESS ? Status.COMPLETED : Status.IN_PROGRESS);
        return mapper.mapToTaskReadOnlyDTO(taskRepository.save(task));
    }
}
