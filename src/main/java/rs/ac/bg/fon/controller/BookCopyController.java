package rs.ac.bg.fon.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.bg.fon.model.dto.BookCopyDTO;
import rs.ac.bg.fon.service.BookCopyService;

@RestController
@RequestMapping("/bookCopies")
public class BookCopyController {

    @Autowired
    private BookCopyService bookService;

    @GetMapping("/{customerId}")
    public ResponseEntity<List<BookCopyDTO>> getBooks(@PathVariable("customerId") Long id) {
        List<BookCopyDTO> books = bookService.findBorrowedBooks(id);
        return !books.isEmpty() ? new ResponseEntity<>(books, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/available/{bookId}")
    public ResponseEntity<Long> availableBookCopiesCount(@PathVariable("bookId") Long id) {
        return new ResponseEntity<>(bookService.getAvailableCopiesCount(id), HttpStatus.OK);
    }

}
