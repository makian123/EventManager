package com.mhohos.EventManager.controller;

import com.mhohos.EventManager.dto.RegistrationRequestDto;
import com.mhohos.EventManager.dto.RegistrationResponseDto;
import com.mhohos.EventManager.dto.UserRegistrationMapper;
import com.mhohos.EventManager.repository.UserRepository;
import com.mhohos.EventManager.service.UserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/api/auth")
public class UserRegistrationController {
    private final UserRegistrationService userRegistrationService;
    private final UserRegistrationMapper userRegistrationMapper;

    @Autowired
    public UserRegistrationController(UserRegistrationService userRegistrationService, UserRegistrationMapper userRegistrationMapper) {
        this.userRegistrationService = userRegistrationService;
        this.userRegistrationMapper = userRegistrationMapper;
    }

    @PostMapping(value="/register")
    public ResponseEntity<RegistrationResponseDto> register(@RequestBody RegistrationRequestDto requestData){
        final var registeredUser = userRegistrationService.registerUser(requestData);
        return ResponseEntity.ok(userRegistrationMapper.toRegistrationResponseDto(registeredUser));
    }
}
