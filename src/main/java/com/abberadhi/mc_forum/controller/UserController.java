package com.abberadhi.mc_forum.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abberadhi.mc_forum.dto.UserDTO;
import com.abberadhi.mc_forum.model.UserEntity;
import com.abberadhi.mc_forum.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserDetails(@PathVariable Integer id) {
        UserEntity user = userService.getUserById(id);
        UserDTO u = mapToUserDTO(user);
        return new ResponseEntity<>(u, HttpStatus.OK);

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
