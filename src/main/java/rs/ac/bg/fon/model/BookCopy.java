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

@Entity
@Table(name = "book_copy")
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

    public BookCopy() {
    }

    public BookCopy(String isbn, BookCopyStatus status, Book book, List<BookRental> bookRentals) {
        this.isbn = isbn;
        this.status = status;
        this.book = book;
        this.bookRentals = bookRentals;
    }

    public List<BookRental> getBookRentals() {
        return bookRentals;
    }

    public void setBookRentals(List<BookRental> bookRentals) {
        this.bookRentals = bookRentals;
    }

    public BookCopy(String isbn, BookCopyStatus status, Book book) {
        this.isbn = isbn;
        this.status = status;
        this.book = book;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public BookCopyStatus getStatus() {
        return status;
    }

    public void setStatus(BookCopyStatus status) {
        this.status = status;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

}
