package md.hackathon.springboot.user_service_app.service;

import md.hackathon.springboot.user_service_app.dto.UserDto;
import md.hackathon.springboot.user_service_app.model.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getUserById(Long id);

    User findByRefreshToken(String refreshToken);

    User findByUsername(String username);

    UserDetails loadUserByUsername(String username);

    User registerUser(UserDto userDto);

    boolean verifyExpiration(String token);

    String createToken(User user);

    User updateUser(Long id, UserDto userDto);

    User updateRefreshToken(String username, String refreshToken);

    void deleteUser(Long id);
}
