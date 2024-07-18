package rs.ac.bg.fon.service.impl;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.model.User;
import rs.ac.bg.fon.repository.CustomerRepository;
import rs.ac.bg.fon.repository.UserRepository;
import rs.ac.bg.fon.service.UserService;
import rs.ac.bg.fon.model.dto.UserDTO;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerRepository customerRepository;

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

    @Override
    public UserDTO updateUser(UserDTO userInfo, String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user != null) {
            user.getCustomer().setFirstname(userInfo.getCustomer().getFirstname());
            user.getCustomer().setLastname(userInfo.getCustomer().getLastname());
            user.getCustomer().setEmail(userInfo.getCustomer().getEmail());
            user.getCustomer().setJmbg(userInfo.getCustomer().getJmbg());
            customerRepository.save(user.getCustomer());
            userRepository.save(user);
            return userInfo;
        }
        return null;
    }

}
