package rs.ac.bg.fon.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.bg.fon.model.BookCopy;
import rs.ac.bg.fon.model.BookCopyStatus;

public interface BookCopyRepository extends JpaRepository<BookCopy, String> {

    @Query("UPDATE BookCopy bc SET bc.status = :status WHERE bc.isbn = :isbn")
    void updateTitle(@Param("isbn") String isbn, @Param("status") BookCopyStatus status);

    BookCopy findByIsbn(String bn);

    @Query(value = "SELECT book_copy.* FROM book_copy JOIN rentals ON book_copy.isbn = rentals.book_id "
                + "WHERE rentals.customer_id = :customerId AND rentals.returned_at = NULL", nativeQuery = true)
    List<BookCopy> findBorrowedBooksByCustomer(Long customerId);

}
