package rs.ac.bg.fon.service.impl;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.model.Customer;
import rs.ac.bg.fon.model.Notification;
import rs.ac.bg.fon.model.Role;
import rs.ac.bg.fon.model.User;
import rs.ac.bg.fon.repository.CustomerRepository;
import rs.ac.bg.fon.repository.UserRepository;
import rs.ac.bg.fon.util.AuthenticationRequest;
import rs.ac.bg.fon.util.AuthenticationResponse;
import rs.ac.bg.fon.util.RegisterRequest;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request, HttpServletResponse response) {
        if (userRepository.findByUsername(request.getUsername()).orElse(null) != null) {
            return null;
        }
        Customer customer = new Customer(
                null,
                request.getFirstname(),
                request.getLastname(),
                request.getJmbg(),
                request.getEmail(),
                new ArrayList<>(),
                null,
                new ArrayList<>());
        customerRepository.save(customer);
        User user = new User(
                null,
                request.getUsername(),
                passwordEncoder.encode(request.getPassword()),
                LocalDate.now().plusYears(1),
                customer,
                new HashSet<>(),
                Role.USER);
        userRepository.save(user);
        return this.prepareResponse(user, response);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request, HttpServletResponse response) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        User user = userRepository.findByUsername(request.getUsername()).orElse(null);
        return this.prepareResponse(user, response);
    }

    private AuthenticationResponse prepareResponse(User user, HttpServletResponse response) {
        String jwtToken = jwtService.generateToken(user);
        Cookie cookie = new Cookie("token", jwtToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(24 * 3600);
        response.addCookie(cookie);
        return AuthenticationResponse.builder()
                .firstname(user.getCustomer().getFirstname())
                .lastname(user.getCustomer().getLastname())
                .username(user.getUsername())
                .role(user.getRole().toString())
                .customerId(user.getCustomer().getId())
                .notifications(user.getNotifications())
                .build();
    }

}
