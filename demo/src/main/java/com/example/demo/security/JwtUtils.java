package com.example.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class JwtUtils {

    @Value("${secret}")
    private String secret;

    public Claims getTokenBody(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateToken(UserDetails userDetails) {

        UserDetailsDemo userDetailsDemo = (UserDetailsDemo) userDetails;

        Map<String, Object> data = new HashMap<>();
        data.put("id", userDetailsDemo.getUtilisateur().getId());

        return Jwts.builder()
                .setClaims(data)
                .setSubject(userDetails.getUsername())
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public boolean tokenValide(String token, UserDetails userDetails) {

        Claims claims = getTokenBody(token);

        return claims.getSubject().equals(userDetails.getUsername());
    }
}
