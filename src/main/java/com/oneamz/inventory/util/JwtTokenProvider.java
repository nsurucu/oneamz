package com.oneamz.inventory.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtTokenProvider {

    private final SecretKey secretKey;

    public JwtTokenProvider(SecretKey secretKey) {
        this.secretKey = secretKey;
    }

    public String createToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 86400000); // 1 gün

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(secretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new SignatureException("Expired or invalid JWT token");
        }
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}
