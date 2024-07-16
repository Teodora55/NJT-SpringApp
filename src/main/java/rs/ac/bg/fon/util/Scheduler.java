package rs.ac.bg.fon.util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import rs.ac.bg.fon.model.BookCopyStatus;
import rs.ac.bg.fon.model.BookRental;
import rs.ac.bg.fon.model.Notification;
import rs.ac.bg.fon.model.User;
import rs.ac.bg.fon.repository.BookCopyRepository;
import rs.ac.bg.fon.repository.BookRentalRepository;
import rs.ac.bg.fon.repository.NotificationRepository;
import rs.ac.bg.fon.repository.UserRepository;

@Component
public class Scheduler {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private BookCopyRepository bookCopyRepository;

    @Autowired
    private BookRentalRepository bookRentalRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Scheduled(cron = "0 0 0 * * ?")
    public void executeTask() {
        sendExpirationNotification(7);
        sendExpirationNotification(3);
        sendExpirationNotification(1);

        expiredMemberships();

        booksSoonToReturn();
        returnBooks();
    }

    private void sendExpirationNotification(int numOfDays) {
        List<User> users = userRepository.findUsersWithMembershipExpiringOn(LocalDate.now().plusDays(numOfDays));
        String message = numOfDays > 1 ? "Your membership is expiring in " + numOfDays + " days. Please extend your membership!"
                : "Your membership is expiring tommorow! If you want to continue to use library, please extend your membership!";
        for (User user : users) {
            Notification notification = new Notification(message,
                    "Membership notification", user, null);
            notificationRepository.save(notification);
            sendExpirationMail(user, numOfDays);
        }
    }

    private void sendExpirationMail(User user, int numOfDays) {
        try {
            String htmlBody = new String(Files.readAllBytes(Paths.get("src/main/resources/templates/membership_expiration.html")));
            htmlBody = htmlBody.replace("{{name}}", user.getUsername());
            String days = numOfDays > 1 ? numOfDays + " days" : "1 day";
            htmlBody = htmlBody.replace("{{numOfDays}}", days);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(user.getCustomer().getEmail());
            helper.setSubject("Membership expiration");
            helper.setText(htmlBody, true);
            ClassPathResource image = new ClassPathResource("/static/image/logo.jpg");
            helper.addInline("logo", image);

            mailSender.send(message);
        } catch (IOException | MessagingException ex) {
            Logger.getLogger(Scheduler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Transactional
    private void expiredMemberships() {
        List<User> users = userRepository.findUsersWithMembershipExpiringOn(LocalDate.now());
        for (User user : users) {
            for (BookRental bookRental : user.getCustomer().getBookRentals()) {
                bookRental.getBookCopy().setStatus(BookCopyStatus.AVAILABLE);
                bookCopyRepository.save(bookRental.getBookCopy());
                bookRental.setReturnedAt(LocalDate.now());
                bookRentalRepository.save(bookRental);
            }
        }

    }

    private void returnBooks() {
        List<BookRental> rentals = bookRentalRepository.findRentalsDue(LocalDate.now());
        for (BookRental rental : rentals) {
            rental.setReturnedAt(LocalDate.now());
            bookRentalRepository.save(rental);
            rental.getBookCopy().setStatus(BookCopyStatus.AVAILABLE);
            bookCopyRepository.save(rental.getBookCopy());
        }
    }

    private void booksSoonToReturn() {
        List<BookRental> rentals = bookRentalRepository.findRentalsDue(LocalDate.now());
        for (BookRental rental : rentals) {
            String message = "Your rental for: '" + rental.getBookCopy().getBook().getName()
                    + "' is about to expire! If you want to continue reading book, please extend returning date.";
            Notification notification = new Notification(message,
                    "Return book", rental.getCustomer().getUser(), null);
            notificationRepository.save(notification);
        }
    }
}
