package com.qlct.core.utils;


import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


@Component
public class JwtTokenUtil {

  @Value("${jwt_secret_key}")
  private String secretKey;
  private final long expirationTimeToken = System.currentTimeMillis() +1000*60 ; // 1 phút
  private final long expirationTimerefreshToken = System.currentTimeMillis() +1000*60*60*24*7; // 7 ngày

  public String generateToken(String username) {
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + expirationTimeToken);

    String token = Jwts.builder()
        .setSubject(username)
        .claim("TOKEN_TYPE", "TOKEN_ACCESS")
        .setIssuedAt(now)
        .setExpiration(expiryDate)
        .signWith(getSigningKey(), SignatureAlgorithm.HS512)
        .compact();

    return "Bearer " + token;
  }
  
  public String generateRefreshToken(String username) {
      return Jwts.builder()
              .setSubject(username)
              .claim("TOKEN_TYPE", "TOKEN_REFRESH")
              .setIssuedAt(new Date())
              .setExpiration(new Date(expirationTimerefreshToken))
              .signWith(getSigningKey(), SignatureAlgorithm.HS512)
              .compact();
  }
  
  private Key getSigningKey() {
      byte[] keyBytes = Decoders.BASE64.decode(secretKey);
      return Keys.hmacShaKeyFor(keyBytes);
  }
  
  public String getUsernameFromToken(String token) {
    Claims claims = Jwts.parserBuilder()
        .setSigningKey(getSigningKey())
        .build()
        .parseClaimsJws(token)
        .getBody();

    return claims.getSubject();
  }
  
  public boolean isRefreshToken(String token) {
      Claims claims = Jwts.parserBuilder()
              .setSigningKey(getSigningKey())
              .build()
              .parseClaimsJws(token)
              .getBody();
      return "TOKEN_REFRESH".equals(claims.get("TOKEN_TYPE"));
  }
  
  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token.replace("Bearer ", ""));
      return true;
    } catch (MalformedJwtException ex) {
      System.out.println("Invalid JWT token!");
    } catch (ExpiredJwtException ex) {
      System.out.println("Expired JWT token!");
    } catch (UnsupportedJwtException ex) {
      System.out.println("Unsupported JWT token!");
    } catch (IllegalArgumentException ex) {
      System.out.println("JWT claims string is empty!");
    }
    return false;
  }
}