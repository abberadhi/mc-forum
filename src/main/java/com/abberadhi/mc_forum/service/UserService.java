package com.abberadhi.mc_forum.service;

import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.abberadhi.mc_forum.model.CommentEntity;
import com.abberadhi.mc_forum.model.UserEntity;
import com.abberadhi.mc_forum.repository.CommentRepository;
import com.abberadhi.mc_forum.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public UserEntity getUserById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    public void createUser(UserDetails newUser) {
        UserEntity user = new UserEntity();
        user.setPassword(newUser.getPassword());
        user.setUsername(newUser.getUsername());

        userRepository.save(user);
    }

    public void updatePost(Integer id, UserEntity updatedUser) throws Exception {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        // Check if user is authorized
        UserEntity userAuthenticated = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).get();
        if (!userAuthenticated.getId().equals(user.getId())) {
            throw new Exception("User not authenticated");
        }

        // Update the fields
        if (!Objects.equals(user.getDescription(), updatedUser.getDescription())) {
            user.setDescription(updatedUser.getDescription());
        }

        if (!Objects.equals(user.getBikeModelEntity(), updatedUser.getBikeModelEntity())) {
            user.setBikeModelEntity(updatedUser.getBikeModelEntity());
        }

        userRepository.save(user);
    }

    public List<CommentEntity> getUserActivityByUsername(String username) {
        UserEntity user = userRepository.findByUsername(username).get();
        Pageable page = PageRequest.of(0, 5, Sort.by("createdAt"));
        List<CommentEntity> recentComments = commentRepository.findAllByUserId(user.getId(), page);

        return recentComments;
    }

    public UserEntity getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
}
