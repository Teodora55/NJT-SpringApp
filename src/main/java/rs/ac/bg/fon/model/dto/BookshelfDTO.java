package rs.ac.bg.fon.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class BookshelfDTO {

    private Long id;
    
    @NotBlank(message = "Bookshelf name is required")
    @Pattern(regexp = "^[a-zA-Z0-9 .,:;-]{2,30}$",
            message = "Bookshelf name is incorrectly formated")
    private String name;
}
