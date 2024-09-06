package rs.ac.bg.fon.service;

import java.util.List;
import rs.ac.bg.fon.model.dto.BookCopyDTO;

public interface BookCopyService {

    BookCopyDTO createBookCopy(BookCopyDTO book);
        
    List<BookCopyDTO> findBorrowedBooks(Long customerId);
    
    Long getAvailableCopiesCount(Long bookId);
}
