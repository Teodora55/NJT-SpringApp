package rs.ac.bg.fon.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationResponse {
    
    private String username;
    private String firstname;
    private String lastname;
    private String role;
    private Long customerId;
    
}
