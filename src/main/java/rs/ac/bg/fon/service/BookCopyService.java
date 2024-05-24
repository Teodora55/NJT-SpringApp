package rs.ac.bg.fon.service;

import java.util.List;
import rs.ac.bg.fon.model.BookCopy;
import rs.ac.bg.fon.model.BookCopyStatus;

public interface BookCopyService {

    BookCopy createBookCopy(BookCopy book);
    
    BookCopy findBookCopy(String isbn);
    
    List<BookCopy> findBorrowedBooks(Long customerId);

    BookCopy updateBookCopyStatus(String isbn, BookCopyStatus bookStatus);

    BookCopy deleteBookCopy(String isbn);
}
