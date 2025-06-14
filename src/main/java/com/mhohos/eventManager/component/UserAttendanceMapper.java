package com.mhohos.eventManager.component;

import com.mhohos.eventManager.dto.UserAttendanceDto;
import com.mhohos.eventManager.model.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserAttendanceMapper {
    public final EventMapper eventMapper;

    public UserAttendanceMapper(EventMapper eventMapper) {
        this.eventMapper = eventMapper;
    }

    public UserAttendanceDto toUserAttendanceDto(User usr){
        return new UserAttendanceDto(usr.getEventsAttending().stream().map(eventMapper::toEventDto).collect(Collectors.toSet()));
    }
}
