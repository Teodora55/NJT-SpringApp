package rs.ac.bg.fon.service;

import java.util.List;
import rs.ac.bg.fon.model.Customer;

public interface CustomerService {

    Customer saveCustomer(Customer book);

    Customer updateCustomer(Long id, Customer book);

    Customer deleteCustomer(Long id);

    Customer getCustomer(Long id);

    List<Customer> getAllCustomers();

}
