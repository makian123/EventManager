package com.mhohos.eventManager.service;

import com.mhohos.eventManager.dto.AttendRequestDto;
import com.mhohos.eventManager.model.Event;
import com.mhohos.eventManager.model.User;
import com.mhohos.eventManager.repository.EventRepository;
import com.mhohos.eventManager.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class EventAttendanceService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;


    public EventAttendanceService(EventRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public boolean registerAttendance(AttendRequestDto attendRequest){
        try {
            Event event = eventRepository.findById(attendRequest.eventId()).orElseThrow();
            User usr = userRepository.findByUsername(attendRequest.username()).orElseThrow();

            if(usr.getEventsAttending().contains(event)){
                return false;
            }

            usr.addAttendance(event);
            return true;
        }
        catch (NoSuchElementException e){
            return false;
        }
    }

    @Transactional
    public boolean unregisterAttendance(AttendRequestDto attendRequest) {
        try {
            Event event = eventRepository.findById(attendRequest.eventId()).orElseThrow();
            User usr = userRepository.findByUsername(attendRequest.username()).orElseThrow();

            if (!usr.getEventsAttending().contains(event)) {
                return false;
            }

            usr.removeAttendance(event);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
