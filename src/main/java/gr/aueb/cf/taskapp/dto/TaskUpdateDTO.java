package gr.aueb.cf.taskapp.dto;

import gr.aueb.cf.taskapp.core.enums.Status;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TaskUpdateDTO {

    @NotNull(message = "Required field")
    private Long id;

    @NotNull(message = "Required field")
    private String title;

    @NotNull(message = "Required field")
    private String description;

    @NotNull(message = "Required field")
    private Status status;

    @NotNull(message = "Required field")
    private LocalDate dueDate;

    @NotNull
    private Long userId;
}
