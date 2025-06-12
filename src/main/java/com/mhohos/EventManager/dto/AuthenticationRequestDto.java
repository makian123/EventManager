package com.mhohos.EventManager.dto;

public record AuthenticationRequestDto(
        String username,
        String password
) {
}
