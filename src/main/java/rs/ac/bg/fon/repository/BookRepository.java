package rs.ac.bg.fon.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.bg.fon.model.Book;
import rs.ac.bg.fon.model.BookCopy;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT bc FROM Book b JOIN b.bookCopies bc WHERE b.id = :bookId")
    List<BookCopy> getAllBookCopies(@Param("bookId") Long bookId);

}
