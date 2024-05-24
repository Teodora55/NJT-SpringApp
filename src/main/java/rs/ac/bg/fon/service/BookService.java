package rs.ac.bg.fon.service;

import java.util.List;
import rs.ac.bg.fon.model.Book;
import rs.ac.bg.fon.model.BookCopy;

public interface BookService {
    
    Book saveBook(Book book);
    
    Book updateBook(Long id,Book book);
    
    Book deleteBook(Long id);
    
    Book getBook(Long id);
    
    List<Book> getAllBooks();

    List<BookCopy> getAllBookCopies(Long bookId);
}
