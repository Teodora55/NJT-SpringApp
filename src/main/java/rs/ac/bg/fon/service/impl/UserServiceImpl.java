package rs.ac.bg.fon.service.impl;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.model.User;
import rs.ac.bg.fon.repository.UserRepository;
import rs.ac.bg.fon.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void extendMembership(String username) {
        LocalDate membershipDate;
        User user = userRepository.findByUsername(username).orElseThrow();
        if (user.isAccountNonExpired()) {
            membershipDate = user.getMembershipExpiration().plusYears(1);
        } else {
            membershipDate = LocalDate.now().plusYears(1);
        }
        userRepository.updateMembershipExpiration(user.getId(), membershipDate);
    }

}
