package rs.ac.bg.fon.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.bg.fon.model.Customer;
import rs.ac.bg.fon.model.dto.CustomerDTO;
import rs.ac.bg.fon.model.mapper.CustomerMapper;
import rs.ac.bg.fon.repository.CustomerRepository;
import rs.ac.bg.fon.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customer) {
        return CustomerMapper.toDto(customerRepository.save(CustomerMapper.toEntity(customer)));
    }

    @Transactional
    @Override
    public CustomerDTO updateCustomer(Long id, CustomerDTO customer) {
        Customer existing = customerRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setFirstname(customer.getFirstname());
            existing.setLastname(customer.getLastname());
            existing.setEmail(customer.getEmail());
            customerRepository.save(existing);
            return customer;
        }
        return null;
    }

    @Transactional
    @Override
    public CustomerDTO deleteCustomer(Long id) {
        Customer existing = customerRepository.findById(id).orElse(null);
        if (existing != null) {
            customerRepository.delete(existing);
            return CustomerMapper.toDto(existing);
        }
        return null;
    }

    @Override
    public CustomerDTO getCustomer(Long id) {
        return CustomerMapper.toDto(customerRepository.findById(id).orElse(null));
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll()
                .stream().map(CustomerMapper::toDto).collect(Collectors.toList());
    }
}
