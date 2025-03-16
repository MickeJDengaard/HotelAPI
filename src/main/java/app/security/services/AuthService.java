package app.security.services;

import app.security.dao.UserDAO;
import app.security.entities.User;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;

public class AuthService {

    private final UserDAO userDAO;

    public AuthService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public boolean login(String username, String password){
        return Optional.ofNullable(userDAO.findByUsername(username))
                .map(user -> BCrypt.checkpw(password, user.getPassword()))
                .orElse(false);
        }
    }


