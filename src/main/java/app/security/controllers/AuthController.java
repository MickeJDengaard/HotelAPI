package app.security.controllers;

import app.security.dto.LoginRequestDTO;
import app.security.services.AuthService;
import io.javalin.http.Context;
import app.security.utils.JwtUtil;
import io.javalin.http.Cookie;

public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    public void login(Context ctx) {
        LoginRequestDTO req = ctx.bodyAsClass(LoginRequestDTO.class);
        boolean success = authService.login(req.getUsername(), req.getPassword());

        if (success) {

            String token = JwtUtil.generateToken(req.getUsername());
            //Opretter en cookie med token
            Cookie jwtCookie = new Cookie("jwt", token);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setSecure(true);
            jwtCookie.setMaxAge(86400);
            jwtCookie.setPath("/"); // Sætter cookien til at være tilgængelig på alle endpoints

            ctx.cookie(jwtCookie); // Sætter cookien i response
            ctx.status(200).result("Login successful!");
        } else {
            ctx.status(401).result("Invalid credentials!");
        }
    }
}
