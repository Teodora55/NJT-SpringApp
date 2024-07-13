package rs.ac.bg.fon.service;

import rs.ac.bg.fon.util.UserDTO;

public interface UserService {
    
    void extendMembership(String username);

    public UserDTO updateUser(UserDTO userInfo, String username);
    
}
