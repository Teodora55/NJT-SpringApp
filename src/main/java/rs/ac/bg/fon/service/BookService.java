package rs.ac.bg.fon.service;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import rs.ac.bg.fon.model.Book;
import rs.ac.bg.fon.model.dto.BookCopyDTO;
import rs.ac.bg.fon.model.dto.BookDTO;

public interface BookService {

    BookDTO saveBook(BookDTO book);

    BookDTO updateBook(Long id, BookDTO book);

    BookDTO deleteBook(Long id);

    Book getBook(Long id);

    List<BookDTO> getAllBooks();

    List<BookCopyDTO> getAllBookCopies(Long bookId);
    
    BookDTO uploadBookCover(Long bookId, MultipartFile file);
}
