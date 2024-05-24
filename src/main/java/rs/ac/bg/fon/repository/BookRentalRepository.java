package rs.ac.bg.fon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import rs.ac.bg.fon.model.BookRental;

public interface BookRentalRepository extends JpaRepository<BookRental, Long> {
	BookRental findOneById(Long id);
        
                        @Query(value = "SELECT r.* FROM rentals r "
                + "WHERE r.isbn = :isbn AND r.customer_id = :customerId AND r.returned_at IS NULL", nativeQuery = true)
        BookRental findByCustomerIdAndIsbnAndReturnedAtIsNull(Long customerId,String isbn);

                @Query(value = "SELECT rentals.* FROM rentals JOIN book_copy ON book_copy.isbn = rentals.isbn "
                + "WHERE book_copy.book_id = :bookId AND rentals.customer_Id = :customerId AND rentals.returned_at IS NULL", nativeQuery = true)
	BookRental findByCustomerIdAndBookIdAndReturnedAtIsNull(Long customerId,Long bookId);

	void deleteByCustomerId(Long customerId);

        @Query(value = "SELECT rentals.* FROM rentals JOIN customer ON customer.id =  rentals.customer_id "
                + " JOIN book_copy ON book_copy.isbn = rentals.book_id JOIN book on book_copy.book_id = book.id "
                + "WHERE book.name LIKE CONCAT('%', :filter, '%') "
                + "OR book_copy.isbn LIKE CONCAT('%', :filter, '%') OR customer.first_name LIKE CONCAT('%', :filter, '%') "
                + "OR customer.last_name LIKE CONCAT('%', :filter, '%')", nativeQuery = true)
	List<BookRental> filterBookRentals(@Param(value = "filter") String filter);

        List<BookRental> findByCustomerId(Long customerId);

        @Query(value = "SELECT * FROM rentals r WHERE MONTH(r.borrowed_at) = :month AND YEAR(r.borrowed_at) = :year", nativeQuery = true)
        List<BookRental> findLastMonthRentals(@Param(value = "month") int month,@Param(value = "year") int year);

}