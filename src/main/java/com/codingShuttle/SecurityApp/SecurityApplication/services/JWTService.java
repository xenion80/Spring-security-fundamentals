package com.codingShuttle.SecurityApp.SecurityApplication.services;

import com.codingShuttle.SecurityApp.SecurityApplication.entities.SessionEntity;
import com.codingShuttle.SecurityApp.SecurityApplication.entities.User;
import com.codingShuttle.SecurityApp.SecurityApplication.repositories.SessionEntityRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class JWTService {
    @Value("${jwt.secretKey}")
    private String jwtsecretKey;

    private final SessionEntityRepository sessionEntityRepository;

    private SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(jwtsecretKey.getBytes(StandardCharsets.UTF_8));
    }
    public String generateAccessToken(User user){
        return Jwts.builder()
                .subject(user.getId().toString())
                .claim("email",user.getEmail())
                .claim("roles", Set.of("ADMIN","USER"))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+1000*60*10))
                .signWith(getSecretKey())
                .compact();


    }
    public String generateRefreshToken(User user){
        return Jwts.builder()
                .subject(user.getId().toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+1000*60*60*24*30*6))
                .signWith(getSecretKey())
                .compact();


    }
    public Long getUserIdFromToken(String token){
        Claims claims = Jwts.parser()
                .verifyWith(getSecretKey())   // correct verification
                .build()
                .parseSignedClaims(token)     // ✅ IMPORTANT (NOT parseClaimsJws)
                .getPayload();

        return Long.valueOf(claims.getSubject());
    }
    public void createorUpdateSession(User user,String token){
        sessionEntityRepository.findByUser(user)
                .ifPresent(sessionEntity -> sessionEntityRepository.deleteByUser(user));
        SessionEntity sessionEntity=new SessionEntity();
        sessionEntity.setUser(user);
        sessionEntity.setToken(token);
        sessionEntityRepository.save(sessionEntity);
    }
    public boolean isSessionidValid(Long userId,String token){
        return sessionEntityRepository.findByToken(token)
                .map(sessionEntity -> sessionEntity.getUser().getId().equals(userId)).orElse(false);
    }
}
