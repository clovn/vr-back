package org.example.vrback.controller;

import org.example.vrback.model.Token;
import org.example.vrback.repository.TokenRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
        return "index";
    }

    @PostMapping
    public String createToken(@RequestParam int activationCount) {
        Token token = new Token();

        do {
            token.setToken(generateNumericToken());
        } while (tokenRepository.existsByToken(token.getToken()));

        token.setActivationCount(activationCount);
        tokenRepository.save(token);

        return "redirect:/admin/tokens";
    }

    private int generateNumericToken() {
        Random random = new Random();
        return random.nextInt(10000000, 100000000);
    }

    @GetMapping("/tokens")
    public String viewTokens(Model model) {
        List<Token> tokens = tokenRepository.findAll();
        model.addAttribute("tokens", tokens);
        return "index";
    }
    @PostMapping("/delete")
    public String deleteToken(@RequestParam int token) {
        tokenRepository.deleteById(token);
        return "redirect:/admin/tokens";
    }

}
