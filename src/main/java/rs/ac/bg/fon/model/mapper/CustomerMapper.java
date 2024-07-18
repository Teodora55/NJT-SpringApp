package rs.ac.bg.fon.model.mapper;

import rs.ac.bg.fon.model.Customer;
import rs.ac.bg.fon.model.dto.CustomerDTO;

public class CustomerMapper {

    public static CustomerDTO toDto(Customer customer) {
        if (customer == null) {
            return null;
        }

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setFirstname(customer.getFirstname());
        customerDTO.setLastname(customer.getLastname());
        customerDTO.setJmbg(customer.getJmbg());
        customerDTO.setEmail(customer.getEmail());

        return customerDTO;
    }

    public static Customer toEntity(CustomerDTO customerDTO) {
        if (customerDTO == null) {
            return null;
        }

        Customer customer = new Customer();
        customer.setId(customerDTO.getId());
        customer.setFirstname(customerDTO.getFirstname());
        customer.setLastname(customerDTO.getLastname());
        customer.setJmbg(customerDTO.getJmbg());
        customer.setEmail(customerDTO.getEmail());

        return customer;
    }
}
