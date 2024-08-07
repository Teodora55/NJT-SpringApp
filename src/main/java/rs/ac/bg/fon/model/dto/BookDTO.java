package rs.ac.bg.fon.model.dto;

import java.util.HashSet;
import lombok.Data;
import java.util.Set;

@Data
public class BookDTO {
    private Long id;
    private String name;
    private String coverUrl;
    private Set<BookshelfDTO> bookshelves;
    private Set<AuthorDTO> authors;

    public BookDTO() {
        this.bookshelves = new HashSet<>();
        this.authors = new HashSet<>();
    }

}
