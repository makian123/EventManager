package com.mhohos.eventManager.dto;

import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

public record EventDto(UUID id, String name, Date startDate) {
}
