package rs.ac.bg.fon.service;

import java.util.List;
import rs.ac.bg.fon.model.BookCopyStatus;
import rs.ac.bg.fon.model.dto.BookCopyDTO;

public interface BookCopyService {

    BookCopyDTO createBookCopy(BookCopyDTO book);
    
    BookCopyDTO findBookCopy(String isbn);
    
    List<BookCopyDTO> findBorrowedBooks(Long customerId);

    BookCopyDTO updateBookCopyStatus(String isbn, BookCopyStatus bookStatus);

    BookCopyDTO deleteBookCopy(String isbn);
    
    Long getAvailableCopiesCount(Long bookId);
}
