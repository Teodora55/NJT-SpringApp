package rs.ac.bg.fon.model.dto;

import lombok.Data;

@Data
public class CustomerDTO {

    private Long id;
    private String firstname;
    private String lastname;
    private String jmbg;
    private String email;

}
