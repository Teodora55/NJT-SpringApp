package rs.ac.bg.fon.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.bg.fon.model.Customer;
import rs.ac.bg.fon.repository.CustomerRepository;
import rs.ac.bg.fon.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private CustomerRepository customerRepository;

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Transactional
    public Customer updateCustomer(Long id, Customer customer) {
        Customer existing = customerRepository.findById(id).orElse(null);
        if (existing != null) {
            customer.setFirstname(existing.getFirstname());
            customer.setLastname(existing.getLastname());
            customer.setJmbg(existing.getJmbg());
            customerRepository.save(customer);
            return customer;
        }
        return null;
    }

    @Transactional
    public Customer deleteCustomer(Long id) {
        Customer existing = customerRepository.findById(id).orElse(null);
        if (existing != null) {
            customerRepository.delete(existing);
            return existing;
        }
        return null;
    }

    public Customer getCustomer(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
}
