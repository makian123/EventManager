package com.mhohos.eventManager.dto;

import java.util.UUID;

public record UserDto(UUID id, String username, boolean admin) {
}
