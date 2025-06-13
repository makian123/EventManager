package com.mhohos.eventManager.controller;

import com.mhohos.eventManager.dto.AttendRequestDto;
import com.mhohos.eventManager.service.EventAttendanceService;
import com.mhohos.eventManager.utility.BearerTokenUtil;
import com.mhohos.eventManager.utility.JwtUtil;
import jakarta.validation.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping(value="/user")
public class UserController {
    private final EventAttendanceService eventAttendanceService;
    private final JwtUtil jwtUtil;

    public UserController(EventAttendanceService eventAttendanceService, JwtUtil jwtUtil) {
        this.eventAttendanceService = eventAttendanceService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping(value="/attend_event")
    public ResponseEntity<String> attendEvent(@RequestHeader (name="Authorization") String jwtToken, @RequestBody Long eventId){
        jwtToken = BearerTokenUtil.extractTokenFromHeader(jwtToken);

        try {
            final String username = jwtUtil.extractClaim("username", jwtToken, String.class).orElseThrow();

            if(eventAttendanceService.registerAttendance(new AttendRequestDto(username, eventId))){
                return ResponseEntity.ok("Successful attendance registration");
            }
        }
        catch(NoSuchElementException e) {
            throw new ValidationException("Error getting username");
        }

        throw new ValidationException("Error registering user to attendance");
    }
    @DeleteMapping(value="/attend_event")
    public ResponseEntity<String> unattendEvent(@RequestHeader (name="Authorization") String jwtToken, @RequestBody Long eventId){
        jwtToken = BearerTokenUtil.extractTokenFromHeader(jwtToken);

        try {
            final String username = jwtUtil.extractClaim("username", jwtToken, String.class).orElseThrow();

            if(eventAttendanceService.unregisterAttendance(new AttendRequestDto(username, eventId))){
                return ResponseEntity.ok("Successful attendance registration");
            }
        }
        catch(NoSuchElementException e) {
            throw new ValidationException("Error getting username");
        }

        throw new ValidationException("Error registering user to attendance");
    }
}
