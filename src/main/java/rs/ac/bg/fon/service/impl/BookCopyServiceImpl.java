package rs.ac.bg.fon.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.model.BookCopy;
import rs.ac.bg.fon.model.BookCopyStatus;
import rs.ac.bg.fon.repository.BookCopyRepository;
import rs.ac.bg.fon.service.BookCopyService;

@Service
public class BookCopyServiceImpl implements BookCopyService {

    @Autowired
    private BookCopyRepository bookCopyRepository;

    public BookCopy createBookCopy(BookCopy book) {
        return bookCopyRepository.save(book);
    }
    
    public BookCopy findBookCopy(String isbn){
        return bookCopyRepository.findByIsbn(isbn);
    }

    public BookCopy updateBookCopyStatus(String isbn, BookCopyStatus bookStatus) {
        BookCopy existing = bookCopyRepository.findByIsbn(isbn);
        if (existing != null) {
            existing.setStatus(bookStatus);
            bookCopyRepository.save(existing);
        }
        return existing;
    }

    public BookCopy deleteBookCopy(String isbn) {
        BookCopy existing = bookCopyRepository.findByIsbn(isbn);
        if (existing != null) {
            bookCopyRepository.delete(existing);
        }
        return existing;    }

    @Override
    public List<BookCopy> findBorrowedBooks(Long customerId) {
        return bookCopyRepository.findBorrowedBooksByCustomer(customerId);
    }

}
