package rs.ac.bg.fon.service.impl;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import rs.ac.bg.fon.model.Book;
import rs.ac.bg.fon.model.BookCopy;
import rs.ac.bg.fon.model.BookCopyStatus;
import rs.ac.bg.fon.model.BookRental;
import rs.ac.bg.fon.model.Customer;
import rs.ac.bg.fon.model.dto.BookRentalDTO;
import rs.ac.bg.fon.repository.BookCopyRepository;
import rs.ac.bg.fon.repository.BookRentalRepository;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class BookRentalServiceImplTest {

    @Mock
    private BookRentalRepository bookRentalRepository;

    @Mock
    private BookCopyRepository bookCopyRepository;

    @InjectMocks
    private BookRentalServiceImpl bookRentalService;

    private Customer customer;
    private Book book;
    private BookCopy bookCopy;
    private BookRental bookRental;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        book = new Book();
        bookCopy = new BookCopy();
        bookCopy.setStatus(BookCopyStatus.AVAILABLE);
        Set<BookCopy> bookCopies = new HashSet<>();
        bookCopies.add(bookCopy);
        book.setBookCopies(bookCopies);
        bookRental = new BookRental(customer, bookCopy);
    }

    @Test
    void testFindById() {
        when(bookRentalRepository.findById(1L)).thenReturn(Optional.of(bookRental));

        BookRental result = bookRentalService.findById(1L);

        assertNotNull(result);
        assertEquals(bookRental, result);
        verify(bookRentalRepository, times(1)).findById(1L);
    }

    @Test
    void testReturnBook() {
        when(bookRentalRepository.findById(1L)).thenReturn(Optional.of(bookRental));

        BookRental result = bookRentalService.returnBook(1L);

        assertNotNull(result);
        assertEquals(BookCopyStatus.AVAILABLE, bookCopy.getStatus());
        assertNotNull(bookRental.getReturnedAt());
        verify(bookRentalRepository, times(1)).save(bookRental);
        verify(bookCopyRepository, times(1)).save(bookCopy);
    }

    @Test
    void testExtendReturnByDate() {
        LocalDate returnBy = bookRental.getReturnBy();
        when(bookRentalRepository.findById(1L)).thenReturn(Optional.of(bookRental));

        BookRental result = bookRentalService.extendReturnByDate(1L);

        assertNotNull(result);
        assertEquals(returnBy.plusWeeks(2), result.getReturnBy());
        verify(bookRentalRepository, times(1)).save(bookRental);
    }

    @Test
    void testCreateRental() {
        when(bookRentalRepository.save(any(BookRental.class))).thenReturn(bookRental);
        when(bookCopyRepository.save(bookCopy)).thenReturn(bookCopy);

        BookRental result = bookRentalService.createRental(customer, book);

        assertNotNull(result);
        assertEquals(BookCopyStatus.RENTED, bookCopy.getStatus());
        verify(bookRentalRepository, times(1)).save(any(BookRental.class));
        verify(bookCopyRepository, times(1)).save(bookCopy);
    }

    @Test
    void testFindCustomersCurrentRentals() {
        List<BookRental> rentals = Arrays.asList(bookRental);
        when(bookRentalRepository.findByCustomerIdAndReturnedAtIsNull(1L)).thenReturn(rentals);

        List<BookRentalDTO> result = bookRentalService.findCustomersCurrentRentals(1L);

        assertEquals(1, result.size());
        verify(bookRentalRepository, times(1)).findByCustomerIdAndReturnedAtIsNull(1L);
    }
}
