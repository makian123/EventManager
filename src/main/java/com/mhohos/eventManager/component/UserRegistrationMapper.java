package com.mhohos.eventManager.component;

import com.mhohos.eventManager.dto.RegistrationRequestDto;
import com.mhohos.eventManager.dto.RegistrationResponseDto;
import com.mhohos.eventManager.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserRegistrationMapper {
    public User toEntity(RegistrationRequestDto registrationRequestDto) {
        final var user = new User();

        user.setUsername(registrationRequestDto.username());
        user.setPassword(registrationRequestDto.password());
        user.setAdmin(false);

        return user;
    }

    public RegistrationResponseDto toRegistrationResponseDto(
            final User user) {

        return new RegistrationResponseDto(user.getUsername(), user.isAdmin());
    }
}
