package rs.ac.bg.fon.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.multipart.MultipartFile;

import rs.ac.bg.fon.model.Author;
import rs.ac.bg.fon.model.Book;
import rs.ac.bg.fon.model.dto.BookDTO;
import rs.ac.bg.fon.model.mapper.AuthorMapper;
import rs.ac.bg.fon.model.mapper.BookMapper;
import rs.ac.bg.fon.repository.BookRepository;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private MultipartFile multipartFile;

    @InjectMocks
    private BookServiceImpl bookService;

    @Value("${file.upload-dir}")
    private String uploadDir;

    private Book book;
    private BookDTO bookDTO;

    @BeforeEach
    void setUp() {
        book = new Book();
        bookDTO = BookMapper.toDto(book);
    }

    @Test
    void testSaveBook() {
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        BookDTO result = bookService.saveBook(bookDTO);

        assertNotNull(result);
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void testUpdateBook() {
        Long bookId = 1L;
        bookDTO.setName("Updated Book");
        bookDTO.setAuthors(Set.of(AuthorMapper.toDto(new Author())));

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);

        BookDTO result = bookService.updateBook(bookId, bookDTO);

        assertNotNull(result);
        assertEquals("Updated Book", result.getName());
        verify(bookRepository, times(1)).findById(bookId);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void testDeleteBook() {
        Long bookId = 1L;

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        BookDTO result = bookService.deleteBook(bookId);

        assertNotNull(result);
        verify(bookRepository, times(1)).findById(bookId);
        verify(bookRepository, times(1)).delete(book);
    }

    @Test
    void testGetBook() {
        Long bookId = 1L;

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        Book result = bookService.getBook(bookId);

        assertNotNull(result);
        assertEquals(book, result);
        verify(bookRepository, times(1)).findById(bookId);
    }

    @Test
    void testGetBookDTO() {
        Long bookId = 1L;

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        BookDTO result = bookService.getBookDTO(bookId);

        assertNotNull(result);
        assertEquals(BookMapper.toDto(book), result);
        verify(bookRepository, times(1)).findById(bookId);
    }

    @Test
    void testGetAllBooks() {
        List<Book> books = Arrays.asList(book);
        when(bookRepository.findAll()).thenReturn(books);

        List<BookDTO> result = bookService.getAllBooks();

        assertEquals(1, result.size());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void testUploadBookCover() throws IOException {
        Long bookId = 1L;
        String fileName = "cover.jpg";
        Path filePath = Paths.get(uploadDir, fileName);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(multipartFile.getOriginalFilename()).thenReturn(fileName);
        when(multipartFile.getBytes()).thenReturn(new byte[0]);

        BookDTO result = bookService.uploadBookCover(bookId, multipartFile);

        assertNotNull(result);
        assertEquals(fileName, result.getCoverUrl());
        verify(bookRepository, times(1)).findById(bookId);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void testUploadBookCoverFails() throws IOException {
        Long bookId = 1L;
        String fileName = "cover.jpg";

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(multipartFile.getOriginalFilename()).thenReturn(fileName);
        when(multipartFile.getBytes()).thenThrow(IOException.class);

        BookDTO result = bookService.uploadBookCover(bookId, multipartFile);

        assertNull(result);
        verify(bookRepository, times(1)).findById(bookId);
        verify(bookRepository, times(0)).save(book);
    }

}
