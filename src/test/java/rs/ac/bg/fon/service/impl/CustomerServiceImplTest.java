package rs.ac.bg.fon.service.impl;

import java.util.ArrayList;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import rs.ac.bg.fon.model.Customer;
import rs.ac.bg.fon.model.dto.CustomerDTO;
import rs.ac.bg.fon.model.mapper.CustomerMapper;
import rs.ac.bg.fon.repository.CustomerRepository;
import rs.ac.bg.fon.service.CustomerService;

@SpringBootTest
public class CustomerServiceImplTest {

    @Autowired
    private CustomerService customerService;

    @MockBean
    private CustomerRepository customerRepository;

    @Test
    void testGetAllCustomers() {
        Customer customer1 = new Customer(1L, "John", "Doe", "123456789", "john.doe@example.com", new ArrayList<>(), null, new ArrayList<>());
        Customer customer2 = new Customer(2L, "Jane", "Doe", "987654321", "jane.doe@example.com", new ArrayList<>(), null, new ArrayList<>());

        List<Customer> customers = Arrays.asList(customer1, customer2);
        when(customerRepository.findAll()).thenReturn(customers);

        List<CustomerDTO> result = customerService.getAllCustomers();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(CustomerMapper.toDto(customer1), result.get(0));
        assertEquals(CustomerMapper.toDto(customer2), result.get(1));
    }
}
