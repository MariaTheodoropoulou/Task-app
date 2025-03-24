package gr.aueb.cf.taskapp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import gr.aueb.cf.taskapp.core.enums.Status;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TaskReadOnlyDTO {
    private Long id;
    private String title;
    private String description;
    private Status status;
    private LocalDate dueDate;
    private LocalDateTime createdAt;
    private LocalDateTime updated;
    private Long userId;

    @Override
    public String toString() {
        return "User tasks { " +
                " title = " + title +
                " ,description = " + description +
                " ,status = " + status +
                " ,due date = " + dueDate +
                " ,created at = " + createdAt +
                " ,updated = " + updated +
                " ,user id = " + userId +
                " } ";
    }
}
