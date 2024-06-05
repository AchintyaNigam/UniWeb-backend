package com.AchintyaNigam.demo.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoder;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTService {

	public String extractUsername(String token) 
	{
		  return extractClaim(token, Claims::getSubject);
	}
	
	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver)
	{
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith((SecretKey) getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
	public String generateToken(String username, int userId) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("userId", userId);  // Add userId to the claims
		return createToken(claims, username);
	}

	public String createToken(Map<String, Object> claims, String username) {
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(username)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
				.signWith(getSignKey(), SignatureAlgorithm.HS256)
				.compact();
	}

	public Integer extractUserId(String token) {
		return extractClaim(token, claims -> (Integer) claims.get("userId"));
	}


	private Key getSignKey()
	{
		byte[] keyBytes = Decoders.BASE64.decode("64d19c8f1fb9c17a5f2ba1cc72294098cb8f3c446a4f8f6cab2d26604921d9cd");
		return Keys.hmacShaKeyFor(keyBytes);
	}

}


