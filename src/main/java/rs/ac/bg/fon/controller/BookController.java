package rs.ac.bg.fon.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.bg.fon.model.dto.BookDTO;
import rs.ac.bg.fon.service.BookService;

@RestController
@RequestMapping("/book")
public class BookController {
    
    @Autowired
    private BookService bookService;
    
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable("id") Long id){
        BookDTO book = bookService.getBookDTO(id);
        return book != null ? new ResponseEntity<BookDTO>(book, HttpStatus.OK) : 
                new ResponseEntity<BookDTO>(HttpStatus.NOT_FOUND);
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<BookDTO>> getBooks(){
        List<BookDTO> books = bookService.getAllBooks();
        return books.isEmpty() ?  new ResponseEntity<List<BookDTO>>(HttpStatus.NOT_FOUND) : new ResponseEntity<List<BookDTO>>(books, HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<BookDTO> saveBooks(@RequestBody BookDTO book){
        return new ResponseEntity<BookDTO>(bookService.saveBook(book), HttpStatus.OK);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable("id") Long id,@RequestBody BookDTO book){
        BookDTO updatedBook = bookService.updateBook(id, book);
        return updatedBook != null ? new ResponseEntity<BookDTO>(HttpStatus.OK) : 
                new ResponseEntity<BookDTO>(HttpStatus.NOT_FOUND);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<BookDTO> deleteBook(@PathVariable("id") Long id){
        BookDTO book = bookService.deleteBook(id);
        return book != null ? new ResponseEntity<BookDTO>(HttpStatus.OK) : 
                new ResponseEntity<BookDTO>(HttpStatus.NOT_FOUND);
    }
}
