package com.mhohos.EventManager.controller;

import com.mhohos.EventManager.model.User;
import com.mhohos.EventManager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/user")
public class UserController {
    private final UserRepository repo;

    @Autowired
    public UserController(UserRepository repo){
        this.repo = repo;
    }

    @PostMapping(value = "/register")
    public String register(@RequestPart("username") String username, @RequestPart("password") String password){
        repo.save(new User(username, password));

        return "registered";
    }
}
