package rs.ac.bg.fon.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

@Entity
@Table(name = "customer")
public class Customer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column
    String firstname;
    
    @Column
    String lastname;
    
    @Column(unique = true)
    String jmbg;
    
    @Column(unique = true)
    String email;
    
    
    	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
	private List<BookRental> bookRentals;

    public List<BookRental> getBookRentals() {
        return bookRentals;
    }

    public void setBookRentals(List<BookRental> bookRentals) {
        this.bookRentals = bookRentals;
    }

    public List<BookRental> getBookRentalApprovals() {
        return bookRentalApprovals;
    }

    public void setBookRentalApprovals(List<BookRental> bookRentalApprovals) {
        this.bookRentalApprovals = bookRentalApprovals;
    }

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "approvedByAdmin")
	private List<BookRental> bookRentalApprovals;

    public Customer() {
    }

    public Customer(Long id, String firstname, String lastname, String jmbg, String username) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.jmbg = jmbg;
        this.email = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
