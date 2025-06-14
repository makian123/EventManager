package com.mhohos.eventManager.dto;

import java.util.UUID;

public record AttendRequestDto(String username, UUID eventId){}
