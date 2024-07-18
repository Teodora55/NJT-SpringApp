package rs.ac.bg.fon.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.bg.fon.service.impl.AuthenticationService;
import rs.ac.bg.fon.service.impl.JwtService;
import rs.ac.bg.fon.util.AuthenticationRequest;
import rs.ac.bg.fon.model.dto.UserDTO;
import rs.ac.bg.fon.util.RegisterRequest;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequest request, HttpServletResponse response) {
        UserDTO authResponse= authenticationService.register(request, response);
        if (authResponse == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(authResponse, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity authenticate(@RequestBody AuthenticationRequest request, HttpServletResponse response) {
        UserDTO authResponse = authenticationService.authenticate(request, response);
        if (authResponse == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(authResponse, HttpStatus.OK);
    }

}
