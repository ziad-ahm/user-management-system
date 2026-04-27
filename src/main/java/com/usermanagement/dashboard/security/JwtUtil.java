package com.usermanagement.dashboard.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    // مدة صلاحية التوكن (ساعة)
    private final long EXPIRATION_TIME = 1000 * 60 * 60;

    private Key getSignKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    // 🔹 إنشاء التوكن
    public String generateToken(String username) {
        return Jwts.builder()
            .setSubject(username) // نخزن اليوزر
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(getSignKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    // 🔹 نجيب اليوزر من التوكن
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    // 🔹 نتأكد إن التوكن لسه صالح
    public boolean isTokenValid(String token, String username) {
        return (extractUsername(token).equals(username) && !isTokenExpired(token));
    }

    // 🔹 هل التوكن منتهي؟
    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    // 🔹 نقرأ كل البيانات من التوكن
    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(getSignKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }
}
