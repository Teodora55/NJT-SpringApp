package rs.ac.bg.fon.model.dto;

import lombok.Data;
import rs.ac.bg.fon.model.BookCopyStatus;

@Data
public class BookCopyDTO {

    private String isbn;
    private BookCopyStatus status;
    private BookDTO book;
}
