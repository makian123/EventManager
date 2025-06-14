package com.mhohos.eventManager.utility;

import com.mhohos.eventManager.config.JwtProperties;
import com.mhohos.eventManager.model.User;
import com.mhohos.eventManager.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Component
public final class JwtUtil {
    private final JwtProperties jwtProperties;
    private final UserRepository userRepository;

    public JwtUtil(JwtProperties jwtProperties, UserRepository userRepository) {
        this.jwtProperties = jwtProperties;
        this.userRepository = userRepository;
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

    public Optional<User> extractUser(String token){
        String uuid = extractClaim("userId", token, String.class).orElse(null);
        if(uuid == null){
            return Optional.empty();
        }
        return userRepository.findById(UUID.fromString(uuid));
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
