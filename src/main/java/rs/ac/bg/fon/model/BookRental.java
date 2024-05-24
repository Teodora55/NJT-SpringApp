package rs.ac.bg.fon.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;
import rs.ac.bg.fon.util.DateHelper;

@Entity
@Table(name = "rentals")
public class BookRental {
    	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "borrowed_at", nullable = false)
	private LocalDate borrowedAt;

	@NotNull(message = "Return by is mandatory to fill!")
	@Future(message = "Return by must be in the future!")
	@Column(name = "return_by", nullable = false)
	private LocalDate returnBy;

	@Column(name = "returned_at")
	private LocalDate returnedAt;

	@NotNull(message =  "Customer is mandatory to fill!")
	@ManyToOne
	@JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
	private Customer customer;

	@NotNull(message = "Book is mandatory to fill!")
	@ManyToOne
	@JoinColumn(name = "isbn", referencedColumnName = "isbn", nullable = false)
        @JsonManagedReference
	private BookCopy bookCopy;

	@ManyToOne
	@JoinColumn(name = "approved_by_admin_id", referencedColumnName = "id")
	private Customer approvedByAdmin;

	public BookRental() {
		this.borrowedAt = DateHelper.getTodayDate();
		this.returnBy = DateHelper.getTodayDate().plusWeeks(1);
	}

	public BookRental(Customer customer, BookCopy bookCopy) {
		this.borrowedAt = DateHelper.getTodayDate();
		this.returnBy = DateHelper.getTodayDate().plusWeeks(1);
		this.customer = customer;
		this.bookCopy = bookCopy;
	}

    public BookCopy getBookCopy() {
        return bookCopy;
    }

    public void setBookCopy(BookCopy bookCopy) {
        this.bookCopy = bookCopy;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getBorrowedAt() {
		return borrowedAt;
	}

	public LocalDate getReturnBy() {
		return returnBy;
	}

	public void setReturnBy(LocalDate returnBy) {
		this.returnBy = returnBy;
	}

	public LocalDate getReturnedAt() {
		return returnedAt;
	}

	public void setReturnedAt(LocalDate returnedAt) {
		this.returnedAt = returnedAt;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Customer getApprovedByAdmin() {
		return approvedByAdmin;
	}

	public void setApprovedByAdmin(Customer approvedByAdmin) {
		this.approvedByAdmin = approvedByAdmin;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookRental other = (BookRental) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "BookRental [id=" + id + ", borrowedAt=" + borrowedAt + ", returnBy=" + returnBy + ", returnedAt="
				+ returnedAt + ", customer=" + customer + ", book=" + bookCopy + ", approvedByAdmin=" + approvedByAdmin
				+ "]";
	}
}
