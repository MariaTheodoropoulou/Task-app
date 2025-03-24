package gr.aueb.cf.taskapp.dto;
import gr.aueb.cf.taskapp.core.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserReadOnlyDTO {
    private String firstname;
    private String lastname;
    private Role role;
    private Set<TaskReadOnlyDTO> tasks;
}
