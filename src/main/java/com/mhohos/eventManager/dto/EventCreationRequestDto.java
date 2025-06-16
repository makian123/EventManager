package com.mhohos.eventManager.dto;

import java.time.Instant;

public record EventCreationRequestDto(String name, Instant startDate) {
}
