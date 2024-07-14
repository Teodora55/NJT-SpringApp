package rs.ac.bg.fon.repository;

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
            + "WHERE book_copy.book_id = :bookId AND rentals.customer_Id = :customerId AND rentals.returned_at IS NULL", nativeQuery = true)
    BookRental findByCustomerIdAndBookIdAndReturnedAtIsNull(Long customerId, Long bookId);

    void deleteByCustomerId(Long customerId);

    List<BookRental> findByCustomerId(Long customerId);

    @Query(value = "SELECT * FROM rentals r WHERE MONTH(r.borrowed_at) = :month AND YEAR(r.borrowed_at) = :year", nativeQuery = true)
    List<BookRental> findLastMonthRentals(@Param(value = "month") int month, @Param(value = "year") int year);

}
