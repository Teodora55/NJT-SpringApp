package rs.ac.bg.fon.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.model.Customer;
import rs.ac.bg.fon.model.Notification;
import rs.ac.bg.fon.model.Role;
import rs.ac.bg.fon.model.Token;
import rs.ac.bg.fon.model.User;
import rs.ac.bg.fon.repository.CustomerRepository;
import rs.ac.bg.fon.repository.NotificationRepository;
import rs.ac.bg.fon.repository.TokenRepository;
import rs.ac.bg.fon.repository.UserRepository;
import rs.ac.bg.fon.util.AuthenticationRequest;
import rs.ac.bg.fon.model.dto.UserDTO;
import rs.ac.bg.fon.model.mapper.UserMapper;
import rs.ac.bg.fon.util.RegisterRequest;
import rs.ac.bg.fon.util.Scheduler;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private JavaMailSender mailSender;

    public UserDTO register(RegisterRequest request, HttpServletResponse response) {
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
                LocalDate.now().plusWeeks(1),
                customer,
                new HashSet<>(),
                Role.USER);
        userRepository.save(user);
        sendWelcomeNotification(user);
        sendWelcomeMail(user);
        return prepareResponse(user, response);
    }

    public UserDTO authenticate(AuthenticationRequest request, HttpServletResponse response) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        User user = userRepository.findByUsername(request.getUsername()).orElse(null);
        return prepareResponse(user, response);
    }

    private UserDTO prepareResponse(User user, HttpServletResponse response) {
        String jwtToken = jwtService.generateToken(user);
        saveToken(jwtToken);
        Cookie cookie = new Cookie("token", jwtToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(24 * 3600);
        response.addCookie(cookie);
        return UserMapper.toDto(user);
    }

    private void saveToken(String jwt) {
        Token token = new Token();
        token.setToken(jwt);
        token.setTokenValid(true);
        tokenRepository.save(token);
    }

    private void sendWelcomeNotification(User user) {
        String message = """
                         Welcome to Fabulous Books online library! We are excited to have you with us. You can now access thousands of books from the comfort of your home.
                         As a special welcome, you will enjoy a free 7-day trial with full access to all our vast collection of books.
                         Enjoy your reading!""";
        Notification notification = new Notification(message, "Welcome " + user.getCustomer().getFirstname(), user, null);
        notificationRepository.save(notification);
        user.getNotifications().add(notification);
    }

    private void sendWelcomeMail(User user) {
        try {
            String htmlBody = new String(Files.readAllBytes(Paths.get("src/main/resources/templates/welcome.html")));
            htmlBody = htmlBody.replace("{{name}}", user.getCustomer().getFirstname());

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(user.getCustomer().getEmail());
            helper.setSubject("Welcome to Fabulous Books");
            helper.setText(htmlBody, true);
            ClassPathResource image = new ClassPathResource("/static/image/logo.jpg");
            helper.addInline("logo", image);

            mailSender.send(message);
        } catch (IOException | MessagingException ex) {
            Logger.getLogger(Scheduler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
