package rs.ac.bg.fon.service;

import java.util.List;
import rs.ac.bg.fon.model.Book;
import rs.ac.bg.fon.model.Customer;
import rs.ac.bg.fon.model.Order;

public interface OrderService {
   
    Order makeOrder(Customer customer,Book book);
    
    Order returnOrder(Customer customer,Book book);
    
    List<Order> findOrdersByCustomer(Customer customer);
    
    List<Order> findOrdersByBook(Book book);
    
    List<Order> findAllOrders();
    
}
