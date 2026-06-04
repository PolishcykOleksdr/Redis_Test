package com.test.task.redis_learn.controller;

import com.test.task.redis_learn.service.VerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * author: user,
 * date: 04.06.2026
 */

@RestController
@RequestMapping("/redis")
@RequiredArgsConstructor
public class RedisController {
    private final VerificationService service;

    @PostMapping("/get")
    public ResponseEntity<String> generateCode(
            @RequestParam String email
    ) {
        return ResponseEntity.ok(service.generateCode(email));
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginByCode(
            @RequestParam String email,
            @RequestParam String code
    ) {
        if (service.acceptCode(email, code)) {
            return ResponseEntity.ok("Welcome!");
        } else {
            return ResponseEntity.status(401).body("Invalid code");
        }
    }
}