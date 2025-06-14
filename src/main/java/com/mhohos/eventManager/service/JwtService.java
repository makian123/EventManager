package com.mhohos.eventManager.service;

import com.mhohos.eventManager.config.JwtProperties;
import com.mhohos.eventManager.dto.AuthenticationRequestDto;
import com.mhohos.eventManager.model.User;
import com.mhohos.eventManager.repository.UserRepository;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class JwtService {
    private final JwtProperties jwtProperties;
    private final JwtEncoder jwtEncoder;
    private final UserRepository userRepository;

    public JwtService(JwtProperties jwtProperties, JwtEncoder jwtEncoder, UserRepository userRepository) {
        this.jwtProperties = jwtProperties;
        this.jwtEncoder = jwtEncoder;
        this.userRepository = userRepository;
    }

    public String generateToken(AuthenticationRequestDto authRequest){
        Instant now = Instant.now();

        String username = authRequest.username();
        User foundUser = userRepository.findByUsername(username).orElseThrow();
        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(now.plusSeconds(jwtProperties.ttl() * 60))
                .claim("username", username)
                .claim("userId", foundUser.getId())
                .claim("admin", foundUser.isAdmin())
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }
}
