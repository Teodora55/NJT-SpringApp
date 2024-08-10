package rs.ac.bg.fon.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.ac.bg.fon.model.BookCopyStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookCopyDTO {

    @NotBlank(message = "ISBN is required")
    @Pattern(regexp = "^(?:\\d{10}|\\d{13})$", message = "ISBN must be a valid 10 or 13 digit number")
    private String isbn;
    
    private BookCopyStatus status;
    
    @NotNull(message = "Book copy need to refer to specific book!")
    private BookDTO book;
}
