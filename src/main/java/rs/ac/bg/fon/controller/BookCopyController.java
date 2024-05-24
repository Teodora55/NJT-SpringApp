package rs.ac.bg.fon.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.bg.fon.model.BookCopy;
import rs.ac.bg.fon.service.BookCopyService;

@RestController
@RequestMapping("/borowed-book")
public class BookCopyController {
    
    @Autowired
    private BookCopyService bookService;
    
        @GetMapping("/{customerId}")
    public ResponseEntity<List<BookCopy>> getBooks(@PathVariable("customerId") Long id){
        List<BookCopy> books = bookService.findBorrowedBooks(id);
        return !books.isEmpty() ? new ResponseEntity<List<BookCopy>>(books, HttpStatus.OK) : 
                new ResponseEntity<List<BookCopy>>(HttpStatus.NOT_FOUND);
    }
    
}
