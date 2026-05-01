package com.bpietrzak.stockmarket.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chaos")
public class ChaosController {
    // CONSTRUCTOR
    public ChaosController() {}

    // chaos endpoint
    @PostMapping
    public ResponseEntity<Void> chaos() {
        new Thread( () -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.exit(0);
        }).start();
        return ResponseEntity.accepted().build();
    }
}
