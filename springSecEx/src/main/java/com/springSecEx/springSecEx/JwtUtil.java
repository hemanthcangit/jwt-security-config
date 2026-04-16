package com.springSecEx.springSecEx;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private final String SECRET = "mySuperSecretKey123456789012345667";

    public Key getJwtKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public String generateToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .issuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60))
                .signWith(getJwtKey())
                .compact();
    }
    public String ExtractUsername(String token){
        return getClaims(token).getSubject();
    }
    // ✅ Validate Token
    public boolean validateToken(String token, String username) {
        return ExtractUsername(token).equals(username);
    }


    private Claims getClaims(String token) {
        return Jwts.parser()   // ✅ THIS WILL WORK NOW
                .setSigningKey(getJwtKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

    }

}
