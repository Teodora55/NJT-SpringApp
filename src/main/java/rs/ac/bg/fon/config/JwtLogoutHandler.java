package rs.ac.bg.fon.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import rs.ac.bg.fon.model.Token;
import rs.ac.bg.fon.repository.TokenRepository;
import rs.ac.bg.fon.service.JwtService;

@Component
public class JwtLogoutHandler implements LogoutHandler{

    @Autowired
    private JwtService jwtService;
    
    @Autowired
    private TokenRepository tokenRepository;
    
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String jwt = jwtService.extractToken(request);
        if(jwt != null) {
            Token token = tokenRepository.findByToken(jwt).orElse(null);
            if(token != null){
                token.setTokenValid(false);
                tokenRepository.save(token);
            }
        }
        
    }
    
}
