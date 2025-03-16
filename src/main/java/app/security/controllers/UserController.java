package app.security.controllers;

import app.security.dto.UserDTO;
import app.security.services.UserService;
import io.javalin.http.Context;

public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // POST /register
    public void register(Context ctx) {
        UserDTO userDTO = ctx.bodyAsClass(UserDTO.class);
        if (userDTO.getUsername() == null || userDTO.getPassword() == null) {
            ctx.status(400).result("Username and password required");
            return;
        }

        try {
            UserDTO createdUser = userService.registerUser(userDTO.getUsername(), userDTO.getPassword());
            ctx.status(201).json(createdUser);
        } catch (RuntimeException e) {
            ctx.status(400).result(e.getMessage());
        }
    }
}
