package rs.ac.bg.fon.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.bg.fon.model.User;
import rs.ac.bg.fon.service.UserService;
import rs.ac.bg.fon.service.impl.JwtService;
import rs.ac.bg.fon.model.dto.UserDTO;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtService jwtService;

    @GetMapping
    public ResponseEntity getLogedUser(@NotNull HttpServletRequest request) {
        try {
            String token = jwtService.extractToken(request);
            if (token == null) {
                throw new UsernameNotFoundException("You are not loged in!");
            }
            String username = jwtService.extractUsername(token);
            User user = (User) userDetailsService.loadUserByUsername(username);
            return new ResponseEntity(user, HttpStatus.OK);
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        } catch (AccountExpiredException e) {
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }
    }

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
