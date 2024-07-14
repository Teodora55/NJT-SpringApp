package rs.ac.bg.fon.service.impl;

import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.model.Author;
import rs.ac.bg.fon.model.Book;
import rs.ac.bg.fon.model.BookCopy;
import rs.ac.bg.fon.model.BookCopyStatus;
import rs.ac.bg.fon.model.Customer;
import rs.ac.bg.fon.model.BookRental;
import rs.ac.bg.fon.repository.BookCopyRepository;
import rs.ac.bg.fon.repository.BookRentalRepository;
import rs.ac.bg.fon.service.BookRentalService;
import rs.ac.bg.fon.util.BookRentalDTO;

@Service
public class BookRentalServiceImpl implements BookRentalService {

    @Autowired
    private BookRentalRepository bookRentalRepository;

    @Autowired
    private BookCopyRepository bookCopyRepository;

    @Override
    public List<BookRentalDTO> findAll() {
        return convertBookRentals(bookRentalRepository.findAll());
    }

    @Override
    public BookRental findById(Long id) {
        return bookRentalRepository.findById(id).orElse(null);
    }

    @Override
    public BookRentalDTO returnBook(Long id) {
        BookRental existing = findById(id);
        if (existing != null) {
            existing.setReturnedAt(LocalDate.now());
            bookRentalRepository.save(existing);
            BookCopy bookCopy = existing.getBookCopy();
            bookCopy.setStatus(BookCopyStatus.AVAILABLE);
            bookCopyRepository.save(bookCopy);
        }
        return convertRental(existing);
    }

    @Override
    public BookRentalDTO extendReturnByDate(Long id) {
        BookRental existing = findById(id);
        if (existing != null) {
            existing.setReturnBy(existing.getReturnBy().plusWeeks(2));
            bookRentalRepository.save(existing);
        }
        return convertRental(existing);
    }

    @Override
    public BookRental findExistingRentalByBookId(Long customerId, Long bookId) {
        return bookRentalRepository.findByCustomerIdAndBookIdAndReturnedAtIsNull(customerId, bookId);
    }

    @Override
    public List<BookRentalDTO> findByCustomer(Long customerId) {
        return convertBookRentals(bookRentalRepository.findByCustomerId(customerId));
    }

    @Override
    public BookRentalDTO createRental(Customer customer, Book book) {
        BookCopy bookCopy = book.getBookCopies().stream()
                .filter(copy -> copy.getStatus() == BookCopyStatus.AVAILABLE)
                .findFirst().orElse(null);
        if (bookCopy == null) {
            return null;
        }
        BookRental rental = new BookRental(customer, bookCopy);
        bookRentalRepository.save(rental);
        bookCopy.setStatus(BookCopyStatus.RENTED);
        bookCopyRepository.save(bookCopy);
        return convertRental(rental);
    }

    private List<BookRentalDTO> convertBookRentals(List<BookRental> rentals) {
        if (rentals == null) {
            return new ArrayList<>();
        }
        List<BookRentalDTO> rentalsDTO = new ArrayList<>();
        for (BookRental rental : rentals) {
            rentalsDTO.add(convertRental(rental));
        }
        return rentalsDTO;
    }

    private BookRentalDTO convertRental(BookRental rental) {
        return BookRentalDTO.builder()
                .id(rental.getId())
                .name(rental.getBookCopy().getBook().getName())
                .author(getAuthors(rental.getBookCopy().getBook()))
                .customer(rental.getCustomer().getFirstname() + " " + rental.getCustomer().getLastname())
                .borrowed(rental.getBorrowedAt())
                .returnBy(rental.getReturnBy())
                .returned(rental.getReturnedAt())
                .build();
    }

    private Set<String> getAuthors(Book book) {
        Set<String> authorsName = new HashSet<>();
        for (Author author : book.getAuthor()) {
            String name = author.getFirstname() + " " + author.getLastname();
            authorsName.add(name);
        }
        return authorsName;
    }

    @Override
    public List<BookRentalDTO> findCustomersCurrentRentals(Long id) {
        return convertBookRentals(bookRentalRepository.findByCustomerIdAndReturnedAtIsNull(id));
    }

}
