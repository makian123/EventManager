package com.mhohos.eventManager.dto;

public record RegistrationResponseDto(
        String username,
        boolean admin
) {
}