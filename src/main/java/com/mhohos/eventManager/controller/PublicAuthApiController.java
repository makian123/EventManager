package com.mhohos.eventManager.controller;

import com.mhohos.eventManager.dto.*;
import com.mhohos.eventManager.model.User;
import com.mhohos.eventManager.repository.UserRepository;
import com.mhohos.eventManager.service.JwtService;
import com.mhohos.eventManager.service.UserRegistrationService;
import jakarta.validation.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value="/api/public")
public class PublicAuthApiController {
    private final UserRegistrationService userRegistrationService;
    private final UserRegistrationMapper userRegistrationMapper;

    private final UserRepository repo;
    private final JwtService jwtService;

    public PublicAuthApiController(UserRegistrationService userRegistrationService, UserRegistrationMapper userRegistrationMapper, UserRepository repo, JwtService jwtService) {
        this.userRegistrationService = userRegistrationService;
        this.userRegistrationMapper = userRegistrationMapper;
        this.repo = repo;
        this.jwtService = jwtService;
    }

    @PostMapping(value="/register")
    public ResponseEntity<RegistrationResponseDto> register(@RequestBody RegistrationRequestDto requestData){
        final var registeredUser = userRegistrationService.registerUser(requestData);
        return ResponseEntity.ok(userRegistrationMapper.toRegistrationResponseDto(registeredUser));
    }

    @PostMapping(value="/login")
    public ResponseEntity<AuthenticationResponseDto> authenticate(@RequestBody final AuthenticationRequestDto authRequest){
        Optional<User> usr = repo.findByUsername(authRequest.username());

        if(usr.isPresent() && BCrypt.checkpw(authRequest.password(), usr.get().getPassword())){
            return ResponseEntity.ok(new AuthenticationResponseDto(jwtService.generateToken(authRequest)));
        }

        throw new ValidationException("Bad request");
    }
}
