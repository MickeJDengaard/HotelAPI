package app.security.controllers;

import app.security.dto.LoginRequestDTO;
import app.security.services.AuthService;
import io.javalin.http.Context;

public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    public void login(Context ctx) {
        LoginRequestDTO req = ctx.bodyAsClass(LoginRequestDTO.class);
        boolean success = authService.login(req.getUsername(), req.getPassword());

        if (success) {
            ctx.status(200).result("Login successful!");
        } else {
            ctx.status(401).result("Invalid credentials!");
        }
    }
}
