package rs.ac.bg.fon.model.mapper;

import java.util.stream.Collectors;
import rs.ac.bg.fon.model.Role;
import rs.ac.bg.fon.model.User;
import rs.ac.bg.fon.model.dto.UserDTO;

public class UserMapper {

    public static UserDTO toDto(User user) {
        if (user == null) {
            return null;
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setRole(user.getRole());
        userDTO.setCustomer(CustomerMapper.toDto(user.getCustomer()));
        userDTO.setNotifications(user.getNotifications()
                .stream().map(NotificationMapper::toDto).collect(Collectors.toSet()));
        userDTO.setMembershipExpiration(user.getMembershipExpiration());

        return userDTO;
    }

    public static User toEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setRole(userDTO.getRole());
        user.setCustomer(CustomerMapper.toEntity(userDTO.getCustomer()));
        user.setNotifications(userDTO.getNotifications()
                .stream().map(NotificationMapper::toEntity).collect(Collectors.toSet()));
        user.setMembershipExpiration(userDTO.getMembershipExpiration());

        return user;
    }
}
