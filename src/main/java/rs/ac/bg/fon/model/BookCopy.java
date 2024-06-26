package rs.ac.bg.fon.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "book_copy")
@NoArgsConstructor
@Data
public class BookCopy {

    @Id
    private String isbn;

    @Column
    private BookCopyStatus status;

    @ManyToOne
    @JoinColumn(name = "book_id")
    @JsonBackReference
    private Book book;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bookCopy")
    @JsonBackReference
    private List<BookRental> bookRentals;

}
