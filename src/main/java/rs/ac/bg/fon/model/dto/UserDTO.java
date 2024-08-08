package rs.ac.bg.fon.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.Set;
import lombok.Data;
import rs.ac.bg.fon.model.Role;

@Data
public class UserDTO {

    @NotBlank(message = "Username is required")
    @Pattern(regexp = "^[a-zA-Z0-9]{6,20}$",
            message = "Username is incorrectly formated")
    private String username;
    private Role role;
    private CustomerDTO customer;
    private Set<NotificationDTO> notifications;
    private LocalDate membershipExpiration;

}
