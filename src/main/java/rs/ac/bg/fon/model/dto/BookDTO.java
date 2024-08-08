package rs.ac.bg.fon.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.HashSet;
import lombok.Data;
import java.util.Set;

@Data
public class BookDTO {
    private Long id;
    @NotBlank(message = "Book name is required")
    @Pattern(regexp = "^[a-zA-Z0-9 .,'!?:;\"-]{2,50}$",
            message = "Book name is incorrectly formated")
    private String name;
    private String coverUrl;
    private Set<BookshelfDTO> bookshelves;
    private Set<AuthorDTO> authors;

    public BookDTO() {
        this.bookshelves = new HashSet<>();
        this.authors = new HashSet<>();
    }

}
