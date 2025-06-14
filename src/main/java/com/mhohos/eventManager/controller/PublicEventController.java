package com.mhohos.eventManager.controller;

import com.mhohos.eventManager.dto.EventDto;
import com.mhohos.eventManager.component.EventMapper;
import com.mhohos.eventManager.repository.EventRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/api/public")
public class PublicEventController {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    public PublicEventController(EventRepository eventRepository, EventMapper eventMapper) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
    }

    @GetMapping(value="/events")
    public ResponseEntity<List<EventDto>> getEvents(@RequestParam(value="limit", required = false) Long maxEntries){
        List<EventDto> events = eventRepository.findAll()
                .stream().map(eventMapper::toEventDto)
                .toList();
        if(maxEntries != null && maxEntries > 0){
            events = events.subList(0, (int) Math.min(maxEntries, events.size()));
        }

        return ResponseEntity.ok(events);
    }
}
