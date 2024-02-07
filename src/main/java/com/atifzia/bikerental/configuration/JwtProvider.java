package com.atifzia.bikerental.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtProvider {
    private final Key jwtSecret = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    @Value("${app.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateJwtToken(String username, Collection<? extends GrantedAuthority> authorities) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        List<String> authorityStrings = authorities.stream()
                .map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());

        return Jwts.builder()
                .setSubject(username)
                .claim("authorities", authorityStrings)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(jwtSecret)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }
    
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            // Log or handle the exception
            return false;
        }
    }
}
