package rs.ac.bg.fon.service;

import jakarta.servlet.http.HttpServletResponse;
import rs.ac.bg.fon.model.dto.UserDTO;
import rs.ac.bg.fon.util.AuthenticationRequest;
import rs.ac.bg.fon.util.RegisterRequest;

public interface AuthenticationService {

    UserDTO register(RegisterRequest request, HttpServletResponse response);

    UserDTO authenticate(AuthenticationRequest request, HttpServletResponse response);

}
