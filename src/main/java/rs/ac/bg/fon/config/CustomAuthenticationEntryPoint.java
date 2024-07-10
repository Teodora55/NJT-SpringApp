package rs.ac.bg.fon.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if (authException instanceof AccountExpiredException) {
            response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE, "Account expired");
        } else if (authException instanceof BadCredentialsException){
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid username or password");
        }
    }

}
