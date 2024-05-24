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
import rs.ac.bg.fon.service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("id") Long id) {
        Customer customer = customerService.getCustomer(id);
        return customer != null ? new ResponseEntity<Customer>(customer, HttpStatus.OK)
                : new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Customer>> getCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return !customers.isEmpty() ? new ResponseEntity<List<Customer>>(customers, HttpStatus.OK)
                : new ResponseEntity<List<Customer>>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Customer> saveCustomers(@RequestBody Customer customer) {
        return new ResponseEntity<Customer>(customerService.saveCustomer(customer), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable("id") Long id, @RequestBody Customer customer) {
        Customer updatedCustomer = customerService.updateCustomer(id, customer);
        return updatedCustomer != null ? new ResponseEntity<Customer>(updatedCustomer, HttpStatus.OK)
                : new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable("id") Long id) {
        Customer customer = customerService.deleteCustomer(id);
        return customer != null ? new ResponseEntity<Customer>(customer, HttpStatus.OK)
                : new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
    }
}
