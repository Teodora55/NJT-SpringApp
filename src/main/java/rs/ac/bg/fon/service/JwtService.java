package rs.ac.bg.fon.service;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    String extractToken(HttpServletRequest request);

    String extractUsername(String token);

    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

    String generateToken(Map<String, Object> claims, UserDetails userDetails);

    String generateToken(UserDetails userDetails);

    boolean isTokenValid(String jwt, UserDetails userDetails);

}
