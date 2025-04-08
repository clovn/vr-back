package org.example.vrback.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.vrback.repository.TokenRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api")
@Slf4j
public class ApiController {

    private final TokenRepository tokenRepository;

    public ApiController(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @PostMapping("/check/{token}")
    public ResponseEntity<Boolean> checkToken(@PathVariable int token) {
        return ResponseEntity.ok(tokenRepository.existsByToken(token));
    }
}
