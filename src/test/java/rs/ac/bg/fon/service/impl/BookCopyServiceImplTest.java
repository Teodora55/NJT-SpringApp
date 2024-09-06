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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import rs.ac.bg.fon.model.BookCopy;
import rs.ac.bg.fon.model.BookCopyStatus;
import rs.ac.bg.fon.model.dto.BookCopyDTO;
import rs.ac.bg.fon.model.mapper.BookCopyMapper;
import rs.ac.bg.fon.repository.BookCopyRepository;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
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
