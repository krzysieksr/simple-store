package com.example.sklep.security;

import com.example.sklep.user.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.crypto.MacProvider;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

import static java.lang.String.format;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil {
    static Key secretKey = MacProvider.generateKey();
    private final String jwtIssuer = "example.io";

    private final Logger logger;

    public String generateAccessToken(User user) {
        return Jwts.builder()
                .setHeaderParam("type", "JWT")
                .setSubject(format("%s,%s", user.getId(), user.getUsername()))
                .setIssuer(jwtIssuer)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000)) // 24h
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public String getUserId(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject().split(",")[0];
    }

    public String getUsername(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject().split(",")[1];
    }

    public Date getExpirationDate(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        secretKey.getEncoded();
        return claims.getExpiration();
    }


    public boolean validate(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(secretKey.getEncoded())
                    .parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature - {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token - {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token - {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token - {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty - {}", ex.getMessage());
        }
        return false;
    }


}
