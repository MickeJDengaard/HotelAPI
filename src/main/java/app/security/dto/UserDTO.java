package app.security.dto;

import app.security.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String username;
    private String password;

    public static UserDTO fromEntity(User user) {
        return new UserDTO(user.getUsername(), null); // Sender ikke adgangskoden videre
    }
}
