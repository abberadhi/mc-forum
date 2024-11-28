package com.abberadhi.mc_forum.service;

import org.springframework.stereotype.Service;

import com.abberadhi.mc_forum.model.UserEntity;
import com.abberadhi.mc_forum.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserEntity getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
}
