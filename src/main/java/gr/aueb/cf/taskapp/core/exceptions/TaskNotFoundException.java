package gr.aueb.cf.taskapp.core.exceptions;

public class TaskNotFoundException extends Exception {

    public TaskNotFoundException(Long id) {
        super("Task with ID " + id + " not found.");
    }
}
