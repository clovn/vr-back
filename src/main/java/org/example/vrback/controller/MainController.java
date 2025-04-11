package org.example.vrback.controller;

import org.example.vrback.repository.TokenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {

    private final TokenRepository tokenRepository;

    public MainController(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @GetMapping
    public String index() {
        return "landing";
    }
}
