package com.mhohos.eventManager.utility;

import com.mhohos.eventManager.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

@Component
public final class JwtUtil {
    private final JwtProperties jwtProperties;

    public JwtUtil(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    private Optional<Claims> getClaims(String token){
        Claims claims;
        try {
            claims = Jwts.parser()
                    .verifyWith(jwtProperties.publicKey())
                    .decryptWith(jwtProperties.privateKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return Optional.empty();
        }

        return Optional.ofNullable(claims);
    }

    public <T> Optional<T> extractClaim(String claim, String token, Class<T> claimType){
        final Optional<Claims> claims = getClaims(token);
        return claims.map(value -> value.get(claim, claimType));

    }

    public boolean isExpired(String token){
        return extractExpiry(token).isBefore(Instant.now());
    }
    public boolean isValid(String token){
        JwtParser parser = Jwts.parser()
                .verifyWith(jwtProperties.publicKey())
                .build();
        return parser.isSigned(token) && !isExpired(token);
    }

    public Instant extractExpiry(String token) {
        final Optional<Claims> claims = getClaims(token);


        if(claims.isPresent()){
            return claims.get().getExpiration().toInstant();
        }
        else{
            return Instant.MIN;
        }
    }
}
