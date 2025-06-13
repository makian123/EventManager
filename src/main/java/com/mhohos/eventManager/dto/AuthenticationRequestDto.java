package com.mhohos.eventManager.dto;

public record AuthenticationRequestDto(
        String username,
        String password
) {
}
