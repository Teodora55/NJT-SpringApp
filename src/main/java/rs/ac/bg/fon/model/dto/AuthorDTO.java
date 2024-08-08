package rs.ac.bg.fon.model.dto;

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
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z '-]{0,28}[a-zA-Z]$",
            message = "Firstname can only contain alphabetic characters, spaces, hyphens, and must be 2-30 characters long")
    private String firstname;
    @NotBlank(message = "Lastname is required")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z '-]{0,28}[a-zA-Z]$",
            message = "Lastname can only contain alphabetic characters, spaces, hyphens, and must be 2-30 characters long")
    private String lastname;
    private int yearOfBirth;
    private Integer yearOfDeath;
}
