package com.mhohos.EventManager.dto;

import com.mhohos.EventManager.model.User;
import jakarta.validation.ValidationException;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Component
public class AuthenticationRequestMapper {
    public User toEntity(AuthenticationRequestDto registrationRequestDto) {
        final var user = new User();

        user.setUsername(registrationRequestDto.username());
        user.setPassword(registrationRequestDto.password());

        return user;
    }

    public AuthenticationResponseDto toAuthenticationResponseDto(
            final User user) {

        /*AuthenticationResponseDto authenticationResponseDto = new AuthenticationResponseDto(
                new Jwt.Builder()
                        .claim("username", user.getUsername())
                        .claim("id", user.getId())
                        .issuedAt(Instant.now())
                        .expiresAt(Instant.now().plusSeconds(15 * 60))
                        .build()
                        .getTokenValue()
        );
        return authenticationResponseDto;
         */
        throw new ValidationException("Err");
    }
}
