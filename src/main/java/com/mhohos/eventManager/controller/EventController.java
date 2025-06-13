package com.mhohos.eventManager.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class EventController {
    @GetMapping(value="/test")
    public String temporary(){
        return "test";
    }
}
