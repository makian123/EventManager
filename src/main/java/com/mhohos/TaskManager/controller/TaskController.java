package com.mhohos.TaskManager.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class TaskController {
    @GetMapping(value="/test")
    public String temporary(){
        return "test";
    }
}
