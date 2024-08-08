package rs.ac.bg.fon.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CustomerDTO {

    private Long id;
    
    @NotBlank(message = "Firstname is required")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z '-]{0,28}[a-zA-Z]$",
            message = "Firstname can only contain alphabetic characters, spaces, hyphens, and must be 2-30 characters long")
    private String firstname;
    
    @NotBlank(message = "Lastname is required")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z '-]{0,28}[a-zA-Z]$",
            message = "Lastname can only contain alphabetic characters, spaces, hyphens, and must be 2-30 characters long")
    private String lastname;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;
    
    @NotBlank(message = "JMBG is required")
    @Pattern(regexp = "^\\d{13}$", message = "JMBG must be a 13 digit string")
    private String jmbg;

}
