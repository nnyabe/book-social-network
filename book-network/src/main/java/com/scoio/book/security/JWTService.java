package com.scoio.book.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {

    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);

    }

    private String generateToken(Map<String, Object>claims, UserDetails userDetails){
        return buildToken(claims, userDetails, jwtExpiration);
    }

    private String buildToken(Map<String, Object>claims, UserDetails userDetails, Long jwtExpiration){
        var authorities = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        return Jwts.builder().
        setClaims(claims).
        subject(null).setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
        .claim("authorities", authorities)
        .signWith(getSignInKey())
        .compact();
    }

    private Key getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
