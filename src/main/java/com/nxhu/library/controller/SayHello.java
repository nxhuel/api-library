package com.nxhu.library.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api")
public class SayHello {

    @GetMapping("/hello")
    public String sayHello() {
        return "hello world";
    }
    
}
