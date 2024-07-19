package rs.ac.bg.fon.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.bg.fon.model.Customer;
import rs.ac.bg.fon.model.dto.CustomerDTO;
import rs.ac.bg.fon.service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable("id") Long id) {
        CustomerDTO customer = customerService.getCustomer(id);
        return customer != null ? new ResponseEntity<>(customer, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CustomerDTO>> getCustomers() {
        List<CustomerDTO> customers = customerService.getAllCustomers();
        return !customers.isEmpty() ? new ResponseEntity<>(customers, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> saveCustomers(@RequestBody CustomerDTO customer) {
        return new ResponseEntity<>(customerService.saveCustomer(customer), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable("id") Long id, @RequestBody CustomerDTO customer) {
        CustomerDTO updatedCustomer = customerService.updateCustomer(id, customer);
        return updatedCustomer != null ? new ResponseEntity<>(updatedCustomer, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomerDTO> deleteCustomer(@PathVariable("id") Long id) {
        CustomerDTO customer = customerService.deleteCustomer(id);
        return customer != null ? new ResponseEntity<>(customer, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
