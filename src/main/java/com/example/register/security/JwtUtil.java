package com.example.register.security;

import com.example.register.entity.Register;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "myverylongsecretkeytmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmhatisatleast32bytes!";

    // ✅ Generate JWT with email, role, and userId
    public String generateToken(Register user) {
        return Jwts.builder()
                .setSubject(user.getEmail())           // username/email
                .claim("role", user.getRole().name())  // role claim
                .claim("userId", user.getId())         // user ID claim
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // Extract username/email from JWT
    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    // Extract role from JWT
    public String extractRole(String token) {
        return getClaims(token).get("role", String.class);
    }

    // Extract userId from JWT
    public Long extractUserId(String token) {
        return getClaims(token).get("userId", Long.class);
    }

    // Validate JWT
    public boolean validateToken(String token, String username) {
        return extractUsername(token).equals(username) && !isTokenExpired(token);
    }

    // Internal method to get claims from token
    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    // Check if token is expired
    private boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }
}
