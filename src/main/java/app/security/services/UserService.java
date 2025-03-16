package app.security.services;

import app.security.dao.UserDAO;
import app.security.dto.UserDTO;
import app.security.entities.User;
import org.mindrot.jbcrypt.BCrypt;

public class UserService {
    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public UserDTO registerUser(String username, String password) {
        if (userDAO.findByUsername(username) != null) {
            throw new RuntimeException("Username already exists");
        }

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        User newUser = new User(null, username, hashedPassword, "USER");

        userDAO.save(newUser);
        return UserDTO.fromEntity(newUser);
    }
}
