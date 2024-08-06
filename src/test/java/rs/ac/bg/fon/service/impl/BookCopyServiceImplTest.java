package rs.ac.bg.fon.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rs.ac.bg.fon.model.BookCopy;
import rs.ac.bg.fon.model.BookCopyStatus;
import rs.ac.bg.fon.model.dto.BookCopyDTO;
import rs.ac.bg.fon.model.mapper.BookCopyMapper;
import rs.ac.bg.fon.repository.BookCopyRepository;

public class BookCopyServiceImplTest {

    @Mock
    private BookCopyRepository bookCopyRepository;

    @InjectMocks
    private BookCopyServiceImpl bookCopyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBookCopy() {
        BookCopyDTO bookCopyDTO = new BookCopyDTO("1234567890", BookCopyStatus.AVAILABLE, null);
        BookCopy bookCopy = BookCopyMapper.toEntity(bookCopyDTO);
        when(bookCopyRepository.save(any(BookCopy.class))).thenReturn(bookCopy);

        BookCopyDTO result = bookCopyService.createBookCopy(bookCopyDTO);

        assertNotNull(result);
        assertEquals("1234567890", result.getIsbn());
        verify(bookCopyRepository).save(any(BookCopy.class));
    }

    @Test
    void testFindBookCopy() {
        String isbn = "1234567890";
        BookCopy bookCopy = new BookCopy(isbn, BookCopyStatus.AVAILABLE, null, null);
        when(bookCopyRepository.findByIsbn(isbn)).thenReturn(bookCopy);

        BookCopyDTO result = bookCopyService.findBookCopy(isbn);

        assertNotNull(result);
        assertEquals(isbn, result.getIsbn());
        verify(bookCopyRepository).findByIsbn(isbn);
    }

    @Test
    void testUpdateBookCopyStatus() {
        String isbn = "1234567890";
        BookCopy existing = new BookCopy(isbn, BookCopyStatus.AVAILABLE, null, null);
        when(bookCopyRepository.findByIsbn(isbn)).thenReturn(existing);
        when(bookCopyRepository.save(any(BookCopy.class))).thenReturn(existing);

        BookCopyDTO result = bookCopyService.updateBookCopyStatus(isbn, BookCopyStatus.RENTED);

        assertNotNull(result);
        assertEquals(isbn, result.getIsbn());
        assertEquals(BookCopyStatus.RENTED, result.getStatus());
        verify(bookCopyRepository).findByIsbn(isbn);
        verify(bookCopyRepository).save(any(BookCopy.class));
    }

    @Test
    void testDeleteBookCopy() {
        String isbn = "1234567890";
        BookCopy existing = new BookCopy(isbn, BookCopyStatus.AVAILABLE, null, null);
        when(bookCopyRepository.findByIsbn(isbn)).thenReturn(existing);

        BookCopyDTO result = bookCopyService.deleteBookCopy(isbn);

        assertNotNull(result);
        verify(bookCopyRepository).findByIsbn(isbn);
        verify(bookCopyRepository).delete(existing);
    }

    @Test
    void testFindBorrowedBooks() {
        Long customerId = 1L;
        List<BookCopy> borrowedBooks = Arrays.asList(
                new BookCopy("1234567890", BookCopyStatus.RENTED, null, null),
                new BookCopy("0987654321", BookCopyStatus.RENTED, null, null)
        );
        when(bookCopyRepository.findBorrowedBooksByCustomer(customerId)).thenReturn(borrowedBooks);

        List<BookCopyDTO> result = bookCopyService.findBorrowedBooks(customerId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("1234567890", result.get(0).getIsbn());
        assertEquals("0987654321", result.get(1).getIsbn());
        verify(bookCopyRepository).findBorrowedBooksByCustomer(customerId);
    }

    @Test
    void testGetAvailableCopiesCount() {
        Long bookId = 1L;
        when(bookCopyRepository.countAvailableCopiesByBookId(bookId)).thenReturn(5L);

        Long result = bookCopyService.getAvailableCopiesCount(bookId);

        assertNotNull(result);
        assertEquals(5L, result);
        verify(bookCopyRepository).countAvailableCopiesByBookId(bookId);
    }
}
