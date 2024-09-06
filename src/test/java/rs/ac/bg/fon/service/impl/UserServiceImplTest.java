package rs.ac.bg.fon.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import rs.ac.bg.fon.model.Customer;
import rs.ac.bg.fon.model.User;
import rs.ac.bg.fon.model.dto.UserDTO;
import rs.ac.bg.fon.model.mapper.CustomerMapper;
import rs.ac.bg.fon.repository.CustomerRepository;
import rs.ac.bg.fon.repository.UserRepository;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testExtendMembership_UserAccountNonExpired() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

        userService.extendMembership("testUser");

        verify(userRepository, times(1)).updateMembershipExpiration(eq(1L), any(LocalDate.class));
    }

    @Test
    public void testExtendMembership_UserAccountExpired() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");
        user.setMembershipExpiration(LocalDate.now().minusWeeks(1));

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

        userService.extendMembership("testUser");

        verify(userRepository, times(1)).updateMembershipExpiration(eq(1L), any(LocalDate.class));
    }

    @Test
    public void testUpdateUser_UserExists() {
        User user = new User();
        user.setUsername("testUser");
        Customer customer = new Customer();
        customer.setFirstname("OldFirstName");
        customer.setLastname("OldLastName");
        customer.setEmail("oldemail@example.com");
        customer.setJmbg("1234567890123");
        user.setCustomer(customer);

        UserDTO userDTO = new UserDTO();
        Customer newCustomer = new Customer();
        newCustomer.setFirstname("NewFirstName");
        newCustomer.setLastname("NewLastName");
        newCustomer.setEmail("newemail@example.com");
        newCustomer.setJmbg("0987654321098");
        userDTO.setCustomer(CustomerMapper.toDto(newCustomer));

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

        UserDTO result = userService.updateUser(userDTO, "testUser");

        verify(customerRepository, times(1)).save(any(Customer.class));
        verify(userRepository, times(1)).save(any(User.class));

        assertNotNull(result);
        assertEquals("NewFirstName", user.getCustomer().getFirstname());
        assertEquals("NewLastName", user.getCustomer().getLastname());
        assertEquals("newemail@example.com", user.getCustomer().getEmail());
        assertEquals("0987654321098", user.getCustomer().getJmbg());
    }

    @Test
    public void testUpdateUser_UserNotFound() {
        UserDTO userDTO = new UserDTO();
        Customer customer = new Customer();
        customer.setFirstname("NewFirstName");
        customer.setLastname("NewLastName");
        customer.setEmail("newemail@example.com");
        customer.setJmbg("0987654321098");
        userDTO.setCustomer(CustomerMapper.toDto(customer));

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        UserDTO result = userService.updateUser(userDTO, "testUser");

        verify(customerRepository, never()).save(any(Customer.class));
        verify(userRepository, never()).save(any(User.class));

        assertNull(result);
    }
}
