package rs.ac.bg.fon.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import rs.ac.bg.fon.model.*;
import rs.ac.bg.fon.model.dto.UserDTO;
import rs.ac.bg.fon.repository.*;
import rs.ac.bg.fon.util.AuthenticationRequest;
import rs.ac.bg.fon.util.RegisterRequest;

import java.util.Optional;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import rs.ac.bg.fon.service.JwtService;

public class AuthenticationServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister_UserAlreadyExists() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("existingUser");
        when(userRepository.findByUsername("existingUser")).thenReturn(Optional.of(new User()));

        UserDTO result = authenticationService.register(request, mock(HttpServletResponse.class));
        assertNull(result);
    }

    @Test
    void testRegister_SuccessfulRegistration() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("newUser");
        request.setFirstname("John");
        request.setLastname("Doe");
        request.setJmbg("123456789");
        request.setEmail("john.doe@example.com");
        request.setPassword("password");

        when(userRepository.findByUsername("newUser")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        HttpServletResponse response = mock(HttpServletResponse.class);
        UserDTO result = authenticationService.register(request, response);

        assertNotNull(result);
        assertEquals("newUser", result.getUsername());
        verify(customerRepository).save(any(Customer.class));
        verify(userRepository).save(any(User.class));
        verify(notificationRepository).save(any(Notification.class));
        verify(mailSender).send(any(MimeMessage.class));
    }

    @Test
    void testAuthenticate_SuccessfulAuthentication() {
        AuthenticationRequest request = new AuthenticationRequest();
        request.setUsername("user");
        request.setPassword("password");

        User user = new User();
        user.setUsername("user");
        user.setPassword("encodedPassword");

        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
        Authentication auth = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(auth);
        when(jwtService.generateToken(user)).thenReturn("jwtToken");

        HttpServletResponse response = mock(HttpServletResponse.class);
        UserDTO result = authenticationService.authenticate(request, response);

        assertNotNull(result);
        assertEquals("user", result.getUsername());
        verify(response).addCookie(any(Cookie.class));
        verify(tokenRepository).save(any(Token.class));
    }
}
