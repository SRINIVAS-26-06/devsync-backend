package com.devsync.devsync_backend.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.devsync.devsync_backend.model.User;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    private final String SECRET_KEY = "mysecretkeymysecretkeymysecretkey1234"; // 32+ chars

    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10 hours

    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    // ✅ Updated to include role + email + sub
    public String generateToken(User user) {
        return Jwts.builder()
                .setClaims(Map.of(
                        "email", user.getEmail(),
                        "role", user.getRole()
                ))
                .setSubject(String.valueOf(user.getId()))  // 👈 sub = userId
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("email", String.class);  // Extract from claim
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            logger.warn("Token has expired: {}", e.getMessage());
            return false;
        } catch (JwtException e) {
            logger.error("Invalid token: {}", e.getMessage());
            return false;
        }
    }
}
