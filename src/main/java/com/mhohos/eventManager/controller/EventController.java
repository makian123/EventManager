package com.mhohos.eventManager.controller;

import com.mhohos.eventManager.dto.EventCreationRequestDto;
import com.mhohos.eventManager.model.Event;
import com.mhohos.eventManager.model.User;
import com.mhohos.eventManager.repository.EventRepository;
import com.mhohos.eventManager.repository.UserRepository;
import com.mhohos.eventManager.utility.BearerTokenUtil;
import com.mhohos.eventManager.utility.JwtUtil;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.UUID;

@RestController
@RequestMapping("/event")
public class EventController {
    private final EventRepository eventRepository;
    private final JwtUtil jwtUtil;

    public EventController(EventRepository eventRepository, JwtUtil jwtUtil) {
        this.eventRepository = eventRepository;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    @DeleteMapping("/{eventId}")
    public void deleteEvent(@RequestHeader (name="Authorization") String jwtToken, @PathVariable UUID eventId){
        Event event = eventRepository.findById(eventId).orElseThrow(ValidationException::new);
        User requestUser = jwtUtil.extractUser(BearerTokenUtil.extractTokenFromHeader(jwtToken)).orElseThrow(ValidationException::new);

        if(!requestUser.getId().equals(event.getOwner().getId()) && !requestUser.isAdmin()){
            throw new ValidationException("Error deleting event");
        }

        for(User attendingUsr: event.getUsersAttending()){
            attendingUsr.removeAttendance(event);
        }

        eventRepository.delete(event);
    }

    @Transactional
    @PutMapping
    public ResponseEntity<UUID> createEvent(@RequestHeader (name="Authorization") String jwtToken, @RequestBody EventCreationRequestDto eventCreationRequest){
        User requestUser = jwtUtil.extractUser(BearerTokenUtil.extractTokenFromHeader(jwtToken)).orElseThrow(ValidationException::new);
        Event createdEvent = new Event(requestUser, eventCreationRequest.name(), eventCreationRequest.startDate());

        eventRepository.save(createdEvent);

        return ResponseEntity.ok(createdEvent.getId());
    }
}
