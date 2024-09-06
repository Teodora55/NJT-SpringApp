package rs.ac.bg.fon.service.impl;

import java.time.LocalDate;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.TestPropertySource;

import rs.ac.bg.fon.model.User;
import rs.ac.bg.fon.repository.UserRepository;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class UserDetailServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailServiceImpl userDetailsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername("nonexistentUser");
        });
    }

    @Test
    public void testLoadUserByUsername_UserFound() {
        User user = new User();
        user.setUsername("existentUser");
        user.setPassword("password");
        user.setMembershipExpiration(LocalDate.now().plusWeeks(1));

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

        UserDetails userDetails = userDetailsService.loadUserByUsername("existentUser");

        assertNotNull(userDetails);
    }

    @Test
    public void testLoadUserByUsername_AccountExpired() {
        User user = new User();
        user.setUsername("expiredUser");
        user.setPassword("password");
        user.setMembershipExpiration(LocalDate.now().minusWeeks(1));

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

        assertThrows(AccountExpiredException.class, () -> {
            userDetailsService.loadUserByUsername("expiredUser");
        });
    }
}
