package com.example.webForDB.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/")
public class MainController {

    @GetMapping("/")
    public String redirectToLoginForm() {
        return "redirect:/login";
    }
}
