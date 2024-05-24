package rs.ac.bg.fon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{
    
}
