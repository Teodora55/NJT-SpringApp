package rs.ac.bg.fon.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.bg.fon.model.Book;
import rs.ac.bg.fon.model.BookCopy;
import rs.ac.bg.fon.repository.BookRepository;
import rs.ac.bg.fon.service.BookService;

@Service
public class BookServiceImpl implements BookService{

    @Autowired
    private BookRepository bookRepository;
    
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    @Transactional
    public Book updateBook(Long id, Book book) {
        Book existing = bookRepository.findById(id).orElse(null);
        if(existing != null){
            book.setName(existing.getName());
            book.setPrice(existing.getPrice());
            book.setAuthor(existing.getAuthor());
            bookRepository.save(book);
            return book;
        }
        return null;
    }

    @Transactional
    public Book deleteBook(Long id) {
        Book existing = bookRepository.findById(id).orElse(null);
        if (existing != null) {
            bookRepository.delete(existing);
            return existing;
        }
        return null;
    }

    public Book getBook(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<BookCopy> getAllBookCopies(Long bookId) {
        return bookRepository.getAllBookCopies(bookId);
    }
    
}
