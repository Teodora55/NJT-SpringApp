package rs.ac.bg.fon.util;

import java.time.LocalDate;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.ac.bg.fon.model.Notification;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String jmbg;
    private String role;
    private Long customerId;
    private Set<Notification> notifications;
    private LocalDate membershipExpiration;

}
