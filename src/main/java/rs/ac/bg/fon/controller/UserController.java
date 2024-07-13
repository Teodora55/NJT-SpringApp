package rs.ac.bg.fon.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.bg.fon.model.Notification;
import rs.ac.bg.fon.service.UserService;
import rs.ac.bg.fon.service.impl.JwtService;
import rs.ac.bg.fon.util.UserDTO;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @PutMapping("/membership")
    public ResponseEntity extendUserMembership(@RequestBody String username) {
        try {
            userService.extendMembership(username);
            return new ResponseEntity(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update")
    public ResponseEntity updateUserInfo(@NotNull HttpServletRequest request, @RequestBody UserDTO user) {
        String username = jwtService.extractUsername(jwtService.extractToken(request));
        UserDTO updatedUser = userService.updateUser(user, username);
        return updatedUser != null
                ? new ResponseEntity(updatedUser, HttpStatus.OK) : new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

}
