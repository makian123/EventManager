package com.mhohos.eventManager.controller;

import com.mhohos.eventManager.dto.AttendRequestDto;
import com.mhohos.eventManager.dto.UserAttendanceDto;
import com.mhohos.eventManager.component.UserAttendanceMapper;
import com.mhohos.eventManager.model.User;
import com.mhohos.eventManager.repository.UserRepository;
import com.mhohos.eventManager.service.EventAttendanceService;
import com.mhohos.eventManager.utility.BearerTokenUtil;
import com.mhohos.eventManager.utility.JwtUtil;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping(value="/user")
public class UserController {
    private final EventAttendanceService eventAttendanceService;
    private final UserAttendanceMapper userAttendanceMapper;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public UserController(EventAttendanceService eventAttendanceService, UserAttendanceMapper userAttendanceMapper, UserRepository userRepository, JwtUtil jwtUtil) {
        this.eventAttendanceService = eventAttendanceService;
        this.userAttendanceMapper = userAttendanceMapper;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    @PostMapping(value="/attend_event")
    public void attendEvent(@RequestHeader (name="Authorization") String jwtToken, @RequestParam(name="eventId") UUID eventId){
        jwtToken = BearerTokenUtil.extractTokenFromHeader(jwtToken);

        try {
            final String username = jwtUtil.extractClaim("username", jwtToken, String.class).orElseThrow();

            if(eventAttendanceService.registerAttendance(new AttendRequestDto(username, eventId))){
                return;
            }
        }
        catch(NoSuchElementException e) {
            throw new ValidationException("User not found");
        }

        throw new ValidationException("Event not found");
    }
    @Transactional
    @DeleteMapping(value="/attend_event")
    public void unattendEvent(@RequestHeader (name="Authorization") String jwtToken, @RequestParam(name="eventId") UUID eventId){
        jwtToken = BearerTokenUtil.extractTokenFromHeader(jwtToken);

        try {
            final String username = jwtUtil.extractClaim("username", jwtToken, String.class).orElseThrow();

            if(eventAttendanceService.unregisterAttendance(new AttendRequestDto(username, eventId))){
                return;
            }
        }
        catch(NoSuchElementException e) {
            throw new ValidationException("Error getting username");
        }

        throw new ValidationException("Error registering user to attendance");
    }

    @GetMapping(value="/events")
    public ResponseEntity<UserAttendanceDto> getEvents(@RequestHeader (name="Authorization") String jwtToken, @RequestParam(required = false) UUID userId){
        jwtToken = BearerTokenUtil.extractTokenFromHeader(jwtToken);
        UUID jwtUserId;
        Boolean isAdministrator;

        try {
            jwtUserId = UUID.fromString(jwtUtil.extractClaim("userId", jwtToken, String.class).orElseThrow());
            isAdministrator = jwtUtil.extractClaim("admin", jwtToken, Boolean.class).orElseThrow();
        }
        catch (NoSuchElementException e){
            throw new ValidationException("Bad JWT token");
        }


        if(userId == null){
            userId = jwtUserId;
        }
        if(!isAdministrator && (!userId.equals(jwtUserId))){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Can't get other users attendance");
        }

        User usr = userRepository.findById(userId).orElseThrow(ValidationException::new);
        return ResponseEntity.ok(userAttendanceMapper.toUserAttendanceDto(usr));
    }
}
