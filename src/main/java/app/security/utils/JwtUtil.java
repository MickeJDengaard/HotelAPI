package app.security.utils;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
public class JwtUtil {
    private static final String SECRET_KEY = "superSecretKey"; // Bør gemmes i .env fil eller lign...
    private static final long EXPIRATION_TIME = 86400000; // 1 dag i millisekunder

    public static String generateToken(String username) {
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }

    public static String validateToken(String token) {
        try {
            DecodedJWT jwt = JWT.require(Algorithm.HMAC256(SECRET_KEY))
                    .build()
                    .verify(token);
            return jwt.getSubject();
        } catch (Exception e) {
            return null; // Ugyldig token
        }
    }
}
