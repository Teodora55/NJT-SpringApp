package rs.ac.bg.fon.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import rs.ac.bg.fon.model.Token;
import rs.ac.bg.fon.repository.TokenRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class JwtServiceTest {

    @InjectMocks
    private JwtServiceImpl jwtService;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private HttpServletRequest request;

    private static final String SECRET_KEY = "6f795c786e3474736164577a5c5d5f3a61324c7a7e54702c2a4d5d7b49";
    private Key signingKey;
    private UserDetails userDetails;
    private String token;

    @BeforeEach
    public void setUp() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        signingKey = Keys.hmacShaKeyFor(keyBytes);
        userDetails = User.builder().username("testuser").password("password").authorities(new ArrayList<>()).build();
        token = Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 3600 * 24))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    @Test
    public void testExtractToken() {
        Cookie cookie = new Cookie("token", token);
        when(request.getCookies()).thenReturn(new Cookie[]{cookie});

        String extractedToken = jwtService.extractToken(request);

        assertNotNull(extractedToken);
        assertEquals(token, extractedToken);
    }

    @Test
    public void testExtractUsername() {
        String username = jwtService.extractUsername(token);
        assertEquals("testuser", username);
    }

    @Test
    public void testGenerateToken() {
        String generatedToken = jwtService.generateToken(userDetails);
        assertNotNull(generatedToken);

        Claims claims = Jwts.parserBuilder().setSigningKey(signingKey).build().parseClaimsJws(generatedToken).getBody();
        assertEquals(userDetails.getUsername(), claims.getSubject());
    }

    @Test
    public void testIsTokenValid() {
        Token validToken = new Token();
        validToken.setToken(token);
        validToken.setTokenValid(true);

        when(tokenRepository.findByToken(token)).thenReturn(Optional.of(validToken));
        boolean isValid = jwtService.isTokenValid(token, userDetails);

        assertTrue(isValid);
    } 

    @Test
    public void testExtractClaim() {
        Date expiration = jwtService.extractClaim(token, Claims::getExpiration);
        assertNotNull(expiration);
    }
}
