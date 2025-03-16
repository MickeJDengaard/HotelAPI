package app.security.middleware;

import app.security.utils.JwtUtil;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.UnauthorizedResponse;

public class JwtMiddleware implements Handler {
    @Override
    public void handle(Context ctx) {
        System.out.println("🔍 JWT Middleware triggered for: " + ctx.path());

        String token = ctx.cookie("jwt");

        if (token == null || JwtUtil.validateToken(token) == null) {
            System.out.println("DEBUG Valde: Unauthorized request to: " + ctx.path());
            throw new UnauthorizedResponse("Unauthorized: Invalid or missing token");
        }

        System.out.println("✅ JWT verified for: " + ctx.path());
    }
}
