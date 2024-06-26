package rs.ac.bg.fon.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.util.Set;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "author")
@NoArgsConstructor
@Data
public class Author {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column
    private String firstname;
    
    @Column
    private String lastname;
    
    @Column
    private int yearOfBirth;
    
    @Column
    private Integer yearOfDeath;
    
    @ManyToMany(mappedBy = "author")
    @JsonBackReference
    private Set<Book> books;

}
