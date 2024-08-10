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
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import rs.ac.bg.fon.util.DateHelper;

@Entity
@Table(name = "rentals")
@Getter
@Setter
@ToString
public class BookRental {
    	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "borrowed_at", nullable = false)
	private LocalDate borrowedAt;

	@Column(name = "return_by", nullable = false)
	private LocalDate returnBy;

	@Column(name = "returned_at")
	private LocalDate returnedAt;

	@ManyToOne
	@JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
	private Customer customer;

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
}
