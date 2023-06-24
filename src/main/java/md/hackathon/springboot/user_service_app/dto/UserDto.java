package md.hackathon.springboot.user_service_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String role;
    private String email;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
}

