package com.oneamz.inventory.util;

import com.oneamz.inventory.util.JwtTokenProvider;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.crypto.SecretKey;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class JwtTokenProviderTest {

    @Mock
    private SecretKey secretKey;

    private JwtTokenProvider tokenProvider;


    @Test
    void testCreateToken() {
        String username = "123ksasTOF.DKSIKSSKKMhsa842339L666666swRDcvRRRSS";
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 86400000); // 1 day

        // Mock a strong 256-bit secret key
        byte[] keyBytes = "123ksasTOF.DKSIKSSKKMhsa842339L666666swRDcvRRRSS".getBytes(); // Replace with a real key
        when(secretKey.getEncoded()).thenReturn(keyBytes);

        String token = tokenProvider.createToken(username);

        // Assert token structure and content
        assertAll(
                () -> assertTrue(token.startsWith("Bearer ")),
                () -> assertEquals(3, token.split("\\.").length),
                () -> assertEquals(username, Jwts.parserBuilder()
                        .setSigningKey(secretKey)
                        .build()
                        .parseClaimsJws(token.substring(7))
                        .getBody()
                        .getSubject()),
                () -> assertEquals(now, Jwts.parserBuilder()
                        .setSigningKey(secretKey)
                        .build()
                        .parseClaimsJws(token.substring(7))
                        .getBody()
                        .getIssuedAt()),
                () -> assertEquals(expiryDate, Jwts.parserBuilder()
                        .setSigningKey(secretKey)
                        .build()
                        .parseClaimsJws(token.substring(7))
                        .getBody()
                        .getExpiration())
        );
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        tokenProvider = new JwtTokenProvider(secretKey);
    }

    @Test
    void testCreateToken1() {
        String username = "123ksasTOF.DKSIKSSKKMhsa842339L666666swRDcvRRRSS";
        String token = tokenProvider.createToken(username);

        // JWT token yapısının doğruluğunu kontrol edin
        assertAll(
                () -> assertTrue(token.startsWith("Bearer ")),
                () -> assertTrue(token.split("\\.").length == 3)
        );
    }

    @Test
    void testValidateToken() {
        String username = "jane.smith";
        String token = tokenProvider.createToken(username);

        assertTrue(tokenProvider.validateToken(token));
    }

    @Test
    void testValidateToken_ExpiredToken() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqYXZhaW51c2UiLCJleHAiOjE2NDU5NjI5NjMsImlhdCI6MTY0NTk2Mjk2M30.954-5435435435435"; // Örnek token

        assertThrows(SignatureException.class, () -> tokenProvider.validateToken(token));
    }

    @Test
    void testValidateToken_InvalidToken() {
        String token = "invalid.token";

        assertThrows(SignatureException.class, () -> tokenProvider.validateToken(token));
    }

    @Test
    void testGetUsernameFromToken() {
        String username = "john.doe";
        String token = tokenProvider.createToken(username);

        assertEquals(username, tokenProvider.getUsernameFromToken(token));
    }
}
