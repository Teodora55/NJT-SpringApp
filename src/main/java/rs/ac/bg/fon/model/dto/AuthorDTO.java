package rs.ac.bg.fon.model.dto;

import lombok.Data;

@Data
public class AuthorDTO {
    private Long id;
    private String firstname;
    private String lastname;
    private int yearOfBirth;
    private Integer yearOfDeath;
}
