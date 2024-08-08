package rs.ac.bg.fon.controller;

import jakarta.validation.Valid;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import rs.ac.bg.fon.model.dto.BookDTO;
import rs.ac.bg.fon.service.BookService;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @GetMapping("/all")
    public ResponseEntity<List<BookDTO>> getBooks() {
        List<BookDTO> books = bookService.getAllBooks();
        return books.isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(books, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BookDTO> saveBooks(@RequestBody @Valid BookDTO book) {
        BookDTO createdBook = bookService.saveBook(book);
        return createdBook != null ? new ResponseEntity<>(createdBook, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable("id") Long id, @RequestBody @Valid BookDTO book) {
        BookDTO updatedBook = bookService.updateBook(id, book);
        return updatedBook != null ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/cover/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable("id") Long id, @RequestParam("file") MultipartFile file) {
        BookDTO updatedBook = bookService.uploadBookCover(id, file);
        return updatedBook != null ? new ResponseEntity<>(updatedBook, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/cover/{path}")
    public ResponseEntity<Resource> loadBookCover(@PathVariable("path") String path) {
        try {
            Path coverPath = Paths.get(uploadDir,path);
            Resource resource = new UrlResource(coverPath.toUri());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        } catch (MalformedURLException ex) {
            Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BookDTO> deleteBook(@PathVariable("id") Long id) {
        BookDTO book = bookService.deleteBook(id);
        return book != null ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
