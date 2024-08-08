package rs.ac.bg.fon.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.bg.fon.service.JwtService;
import rs.ac.bg.fon.util.AuthenticationRequest;
import rs.ac.bg.fon.model.dto.UserDTO;
import rs.ac.bg.fon.service.AuthenticationService;
import rs.ac.bg.fon.util.RegisterRequest;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterRequest request, BindingResult result,
            HttpServletResponse response) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors().toString(), HttpStatus.BAD_REQUEST);
        }
        UserDTO authResponse = authenticationService.register(request, response);
        if (authResponse == null) {
            return new ResponseEntity("Username or password is not valid! ", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(authResponse, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity authenticate(@RequestBody @Valid AuthenticationRequest request, BindingResult result,
            HttpServletResponse response) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors().toString(), HttpStatus.BAD_REQUEST);
        }
        UserDTO authResponse = authenticationService.authenticate(request, response);
        if (authResponse == null) {
            return new ResponseEntity("Username or password is not valid! ", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(authResponse, HttpStatus.OK);
    }

}
