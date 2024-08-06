package rs.ac.bg.fon.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.model.dto.CustomerDTO;
import rs.ac.bg.fon.model.mapper.CustomerMapper;
import rs.ac.bg.fon.repository.CustomerRepository;
import rs.ac.bg.fon.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll()
                .stream().map(CustomerMapper::toDto).collect(Collectors.toList());
    }
}
