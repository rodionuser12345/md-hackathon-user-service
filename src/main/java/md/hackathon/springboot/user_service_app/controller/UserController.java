package md.hackathon.springboot.user_service_app.controller;

import jakarta.validation.Valid;
import md.hackathon.springboot.user_service_app.dto.TokenRefreshRequest;
import md.hackathon.springboot.user_service_app.dto.TokenRefreshResponse;
import md.hackathon.springboot.user_service_app.dto.UserDto;
import md.hackathon.springboot.user_service_app.exception.TokenRefreshException;
import md.hackathon.springboot.user_service_app.model.User;
import md.hackathon.springboot.user_service_app.security.jwt.JwtUtil;
import md.hackathon.springboot.user_service_app.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserServiceImpl userServiceImpl;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserController(UserServiceImpl userServiceImpl, JwtUtil jwtUtil) {
        this.userServiceImpl = userServiceImpl;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto) {
        User user = userServiceImpl.registerUser(userDto);
        UserDto responseDto = new UserDto(user.getId(), user.getUsername(), user.getRole(), user.getEmail(),
                user.getFirstName(), user.getLastName(), user.getDateOfBirth().toString());
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userServiceImpl.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userServiceImpl.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        User user = userServiceImpl.updateUser(id, userDto);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userServiceImpl.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();
        User user = userServiceImpl.findByRefreshToken(requestRefreshToken);
        if (user.getUsername().equals(request.getUsername()) && userServiceImpl.verifyExpiration(requestRefreshToken)) {
            String newToken = userServiceImpl.createToken(user);
            String newRefreshToken = jwtUtil.refreshToken(user.getUsername());
            user.setRefreshToken(newRefreshToken);
            userServiceImpl.updateRefreshToken(user.getUsername(), newRefreshToken);
            return ResponseEntity.ok(new TokenRefreshResponse(newToken, newRefreshToken, user.getUsername()));
        } else {
            throw new TokenRefreshException(requestRefreshToken, "Refresh token is not in database!");
        }
    }

}
