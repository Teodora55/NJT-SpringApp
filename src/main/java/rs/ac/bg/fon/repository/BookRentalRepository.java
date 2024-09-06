package rs.ac.bg.fon.repository;

import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import rs.ac.bg.fon.model.BookRental;

public interface BookRentalRepository extends JpaRepository<BookRental, Long> {

    @Query(value = "SELECT r.* FROM rentals r "
            + "WHERE r.customer_id = :customerId AND r.returned_at IS NULL", nativeQuery = true)
    List<BookRental> findByCustomerIdAndReturnedAtIsNull(Long customerId);

    @Query(value = "SELECT rentals.* FROM rentals JOIN book_copy ON book_copy.isbn = rentals.isbn "
            + "WHERE book_copy.book_id = :bookId AND rentals.customer_Id = :customerId AND rentals.returned_at IS NULL",
            nativeQuery = true)
    BookRental findByCustomerIdAndBookIdAndReturnedAtIsNull(Long customerId, Long bookId);

    void deleteByCustomerId(Long customerId);

    List<BookRental> findByCustomerId(Long customerId);

    @Query("SELECT br FROM BookRental br WHERE br.returnBy = :day AND br.returnedAt IS NULL")
    List<BookRental> findRentalsDue(@Param("day") LocalDate day);

}
