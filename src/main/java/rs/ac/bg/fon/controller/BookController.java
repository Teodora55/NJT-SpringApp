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
import rs.ac.bg.fon.model.Book;
import rs.ac.bg.fon.service.BookService;

@RestController
@RequestMapping("/book")
public class BookController {
    
    @Autowired
    private BookService bookService;
    
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") Long id){
        Book book = bookService.getBook(id);
        return book != null ? new ResponseEntity<Book>(book, HttpStatus.OK) : 
                new ResponseEntity<Book>(HttpStatus.NOT_FOUND);
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<Book>> getBooks(){
        List<Book> books = bookService.getAllBooks();
        return books.isEmpty() ?  new ResponseEntity<List<Book>>(HttpStatus.NOT_FOUND) : new ResponseEntity<List<Book>>(books, HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<Book> saveBooks(@RequestBody Book book){
        return new ResponseEntity<Book>(bookService.saveBook(book), HttpStatus.OK);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable("id") Long id,@RequestBody Book book){
        Book updatedBook = bookService.updateBook(id, book);
        return updatedBook != null ? new ResponseEntity<Book>(HttpStatus.OK) : 
                new ResponseEntity<Book>(HttpStatus.NOT_FOUND);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable("id") Long id){
        Book book = bookService.deleteBook(id);
        return book != null ? new ResponseEntity<Book>(HttpStatus.OK) : 
                new ResponseEntity<Book>(HttpStatus.NOT_FOUND);
    }
}
