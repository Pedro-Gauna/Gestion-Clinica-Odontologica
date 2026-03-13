package com.example.Clinica_Odontologica.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;


//Clave: J3mjVcMVDXsLG6I3DHkvhdf6p9CKqonyMvMT2xnlGoncUVNnda96+2mTR6dSZeVl
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration-ms}")
    private long expirationMs;

    private SecretKey key;

    @PostConstruct
    public void init(){
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(UserDetails user){
        Date now = new Date();
        Date exp = new Date(now.getTime() + expirationMs);

        //Username y authorities
        List<String> roles = user.getAuthorities().stream()
                .map(a -> a.getAuthority())
                .toList();

        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("roles",roles)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(key,SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token, UserDetails user){
        try{
            String username = extractUsername(token);
            return username.equals(user.getUsername()) && !isTokenExpired(token);
        }catch (JwtException | IllegalArgumentException ex){
            return false;
        }
    }




    public boolean isTokenExpired(String token) {
        Date exp = parseClaims(token).getExpiration();
        return exp.before(new Date());
    }

    public String extractUsername(String token) {
        return parseClaims(token).getSubject();
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


}
