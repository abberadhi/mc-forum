package com.abberadhi.mc_forum.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abberadhi.mc_forum.dto.CommentDTO;
import com.abberadhi.mc_forum.dto.UserDTO;
import com.abberadhi.mc_forum.model.CommentEntity;
import com.abberadhi.mc_forum.model.UserEntity;
import com.abberadhi.mc_forum.service.CommentService;
import com.abberadhi.mc_forum.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final CommentService commentService;

    public UserController(UserService userService, CommentService commentService) {
        this.userService = userService;
        this.commentService = commentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserDetails(@PathVariable Integer id) {
        UserEntity user = userService.getUserById(id);
        UserDTO u = mapToUserDTO(user);
        return new ResponseEntity<>(u, HttpStatus.OK);
    }

    @GetMapping("/details/{username}")
    public ResponseEntity<UserDTO> getUserDetails(@PathVariable String username) {
        UserEntity user = userService.getUserByUsername(username);
        UserDTO u = mapToUserDTO(user);
        return new ResponseEntity<>(u, HttpStatus.OK);
    }

    @GetMapping("/activity/{username}")
    public ResponseEntity<List<CommentDTO>> getUserActivity(@PathVariable String username) {
        List<CommentEntity> comments = userService.getUserActivityByUsername(username);
        List<CommentDTO> commentDTO = comments.stream()
                .map(commentService::mapToCommentDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(commentDTO, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable Integer id, @RequestBody UserEntity user) {
        try {
            userService.updatePost(id, user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    public UserDTO mapToUserDTO(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }

        UserDTO dto = new UserDTO();

        dto.setId(userEntity.getId());
        dto.setUsername(userEntity.getUsername());
        dto.setDescription(userEntity.getDescription());
        dto.setDateJoined(userEntity.getDateJoined());
        dto.setBike(userEntity.getBikeModelEntity());

        return dto;
    }
}
