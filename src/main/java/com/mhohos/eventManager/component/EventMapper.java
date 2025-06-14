package com.mhohos.eventManager.component;

import com.mhohos.eventManager.dto.EventDto;
import com.mhohos.eventManager.model.Event;
import com.mhohos.eventManager.repository.EventRepository;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {
    private final EventRepository eventRepository;

    public EventMapper(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public EventDto toEventDto(Event event){
        return new EventDto(event.getId(), event.getName(), event.getStartDate());
    }
    public Event toEvent(EventDto eventDto){
        return eventRepository.findById(eventDto.id()).orElseThrow();
    }
}
