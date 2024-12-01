package com.abberadhi.mc_forum.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.abberadhi.mc_forum.model.UserEntity;
import com.abberadhi.mc_forum.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void createUser(UserDetails newUser) {
        UserEntity user = new UserEntity();
        user.setPassword(newUser.getPassword());
        user.setUsername(newUser.getUsername());

        userRepository.save(user);
    }

    public UserEntity getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
}
