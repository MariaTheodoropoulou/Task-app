package gr.aueb.cf.taskapp.dto;

import gr.aueb.cf.taskapp.core.enums.Role;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserInsertDTO {

    @NotNull(message = "Required field")
    private String username;

    @NotNull(message = "Required field")
    private String password;

    @NotNull(message = "Required field")
    private String firstname;

    @NotNull(message = "Required field")
    private String lastname;

    @NotNull(message = "Required field")
    private Role role;

}
