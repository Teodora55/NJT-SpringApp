package rs.ac.bg.fon.service;

import java.util.List;
import rs.ac.bg.fon.model.dto.CustomerDTO;

public interface CustomerService {

    CustomerDTO saveCustomer(CustomerDTO book);

    CustomerDTO updateCustomer(Long id, CustomerDTO book);

    CustomerDTO deleteCustomer(Long id);

    CustomerDTO getCustomer(Long id);

    List<CustomerDTO> getAllCustomers();

}
