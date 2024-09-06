package rs.ac.bg.fon.controller;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ResponseEntity<List<BookCopyDTO>> getBookCopies(@PathVariable("customerId") Long id) {
        List<BookCopyDTO> books = bookService.findBorrowedBooks(id);
        return !books.isEmpty() ? new ResponseEntity<>(books, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/available/{bookId}")
    public ResponseEntity<Long> availableBookCopiesCount(@PathVariable("bookId") Long id) {
        return new ResponseEntity<>(bookService.getAvailableCopiesCount(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity saveBookCopy(@RequestBody @Valid BookCopyDTO bookCopy, BindingResult result) {
        if (result.hasErrors()) {
            String errorMessage = result.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining("; "));
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
        return bookService.createBookCopy(bookCopy) != null
                ? new ResponseEntity<>("Book copy is created successfully!", HttpStatus.OK)
                : new ResponseEntity<>("There were problem saving book copy!", HttpStatus.BAD_REQUEST);
    }

}
