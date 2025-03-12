package gr.aueb.cf.taskapp.dto;
import gr.aueb.cf.taskapp.core.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserReadOnlyDTO {
    private String firstname;
    private String lastname;
    private Role role;
    private List<TaskReadOnlyDTO> task;
}
