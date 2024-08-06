package rs.ac.bg.fon.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDTO {
    private Long id;
    private String firstname;
    private String lastname;
    private int yearOfBirth;
    private Integer yearOfDeath;
}
