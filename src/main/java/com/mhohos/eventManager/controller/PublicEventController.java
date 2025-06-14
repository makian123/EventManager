package com.mhohos.eventManager.controller;

import com.mhohos.eventManager.dto.EventDto;
import com.mhohos.eventManager.component.EventMapper;
import com.mhohos.eventManager.repository.EventRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public ResponseEntity<List<EventDto>> getEvents(
            @RequestParam(value="page", required = false) Integer pageNumber,
            @RequestParam(value="sort", required = false) String sorting,
            @RequestParam(value="orderBy", required = false) String ordering
    ){
        // Data sanitization
        if(pageNumber == null || pageNumber < 1){
            pageNumber = 1;
        }
        if(sorting == null){
            sorting = "name";
        }

        // Set sorting algorithm
        Sort orderingAlg = Sort.by(sorting);
        if(ordering != null) {
            if (ordering.equals("asc")) {
                orderingAlg = orderingAlg.ascending();
            }
            else if (ordering.equals("desc")) {
                orderingAlg = orderingAlg.descending();
            }
        }

        Pageable sortedData = PageRequest.of(pageNumber - 1, 5, orderingAlg);
        List<EventDto> events = eventRepository.findAll(sortedData)
                .stream()
                .map(eventMapper::toEventDto)
                .toList();

        return ResponseEntity.ok(events);
    }
}
