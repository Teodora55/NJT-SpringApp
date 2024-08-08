package rs.ac.bg.fon.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class BookshelfDTO {

    private Long id;
    @NotBlank(message = "Bookshelf name is required")
    @Pattern(regexp = "^[a-zA-Z0-9 .,:;-]{2,30}$",
            message = "Bookshelf name must be 2-30 characters long and can contain letters, numbers, spaces, and punctuation marks")
    private String name;
}
