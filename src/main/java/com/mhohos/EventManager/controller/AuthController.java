package com.mhohos.EventManager.controller;

import com.mhohos.EventManager.dto.AuthenticationRequestDto;
import com.mhohos.EventManager.dto.AuthenticationResponseDto;
import com.mhohos.EventManager.model.User;
import com.mhohos.EventManager.repository.UserRepository;
import com.mhohos.EventManager.service.JwtService;
import jakarta.validation.ValidationException;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value="/api/auth")
public class AuthController {
    private final UserRepository repo;
    private final JwtService jwtService;

    @Autowired
    public AuthController(UserRepository repo, JwtService jwtService) {
        this.repo = repo;
        this.jwtService = jwtService;
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
