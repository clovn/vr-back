package org.example.vrback.controller;

import org.example.vrback.model.Token;
import org.example.vrback.repository.TokenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Random;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final TokenRepository tokenRepository;

    public AdminController(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @ModelAttribute(name = "tokens")
    public Iterable<Token> getTokens() {
        return tokenRepository.findAll();
    }

    @GetMapping
    public String admin() {
        return "admin";
    }

    @PostMapping
    public void createToken() {
        Token token = new Token();

        do {
            token.setToken(generateNumericToken());
        } while (tokenRepository.existsByToken(token.getToken()));

        tokenRepository.save(token);
    }

    @DeleteMapping()
    public void deleteToken(@RequestParam int token) {
        tokenRepository.deleteById(token);
    }

    private int generateNumericToken() {
        Random random = new Random();
        return random.nextInt(10000000, 100000000);
    }
}
