package com.abberadhi.mc_forum.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.abberadhi.mc_forum.model.AuthenticationRequest;
import com.abberadhi.mc_forum.model.AuthenticationResponse;
import com.abberadhi.mc_forum.model.RegisterRequest;
import com.abberadhi.mc_forum.model.UserEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authentuthenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        UserDetails user = User
            .builder()
            .username(request.getUsername())
            .password(passwordEncoder.encode(request.getPassword()))
            // .roles(Role.example) add role 
            .build();

        userService.createUser(user); // create user to database
        
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authentuthenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        UserEntity user = userService.getUserByUsername(request.getUsername());

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
