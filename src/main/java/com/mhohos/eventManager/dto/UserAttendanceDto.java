package com.mhohos.eventManager.dto;

import com.mhohos.eventManager.model.Event;

public record UserAttendanceDto(java.util.Set<EventDto> events){}
