package rs.ac.bg.fon.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.ac.bg.fon.model.BookCopyStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookCopyDTO {

    private String isbn;
    private BookCopyStatus status;
    private BookDTO book;
}
