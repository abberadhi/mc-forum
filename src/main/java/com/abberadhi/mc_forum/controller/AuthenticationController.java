package com.abberadhi.mc_forum.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.abberadhi.mc_forum.model.AuthenticationRequest;
import com.abberadhi.mc_forum.model.AuthenticationResponse;
import com.abberadhi.mc_forum.model.RegisterRequest;
import com.abberadhi.mc_forum.service.AuthenticationService;




@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registerUser(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticateUser(@RequestBody AuthenticationRequest request) {  
        return ResponseEntity.ok(authService.authenticate(request));
        
    }
    
}
