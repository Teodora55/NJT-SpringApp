package rs.ac.bg.fon.util;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "Firstname is required")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z '-]{0,28}[a-zA-Z]$",
            message = "Firstname is incorrectly formated")
    private String firstname;

    @NotBlank(message = "Lastname is required")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z '-]{0,28}[a-zA-Z]$",
            message = "Lastname is incorrectly formated")
    private String lastname;

    @NotBlank(message = "Username is required")
    @Pattern(regexp = "^[a-zA-Z0-9]{5,20}$",
            message = "Username is incorrectly formated")
    private String username;

    @NotBlank(message = "Password is required")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must be at least 8 characters long, contain at least one uppercase letter, "
            + "one lowercase letter, one digit, and one special character")
    private String password;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "JMBG is required")
    @Pattern(regexp = "^\\d{13}$", message = "JMBG must be a 13 digit string")
    private String jmbg;

}
