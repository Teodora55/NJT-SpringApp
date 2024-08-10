package rs.ac.bg.fon.service;

import java.util.List;
import rs.ac.bg.fon.model.dto.BookshelfDTO;

public interface BookshelfService {

    List<BookshelfDTO> getAllBookshelves();

    BookshelfDTO saveBookshelves(BookshelfDTO bookshelf);
}
