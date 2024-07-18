package rs.ac.bg.fon.model.dto;

import java.time.LocalDate;
import java.util.Set;
import lombok.Data;

@Data
public class UserDTO {

    private String username;
    private String role;
    private CustomerDTO customer;
    private Set<NotificationDTO> notifications;
    private LocalDate membershipExpiration;

}
