package com.mhohos.eventManager.service;

import com.mhohos.eventManager.dto.RegistrationRequestDto;
import com.mhohos.eventManager.model.User;
import com.mhohos.eventManager.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationService {
    private final UserRepository repo;


    public UserRegistrationService(UserRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public User registerUser(RegistrationRequestDto request) {
        if(repo.existsByUsername(request.username())){
            throw new ValidationException("Username already exists");
        }

        return repo.save(new User(request.username(), request.password(), false));
    }
}
