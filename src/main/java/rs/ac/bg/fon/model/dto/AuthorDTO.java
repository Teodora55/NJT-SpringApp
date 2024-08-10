package rs.ac.bg.fon.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDTO {

    private Long id;
    
    @NotBlank(message = "Firstname is required")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z '-.]{0,28}[a-zA-Z.]$",
            message = "Firstname is incorrectly formated")
    private String firstname;
    
    @NotBlank(message = "Lastname is required")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z '-.]{0,28}[a-zA-Z.]$",
            message = "Lastname is incorrectly formated")
    private String lastname;
    
    @Max(value = 2020, message = "Year of birth must be less than or equal to 2020")
    private int yearOfBirth;
    
    private Integer yearOfDeath;
}
