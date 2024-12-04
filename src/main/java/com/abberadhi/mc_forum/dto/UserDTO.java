package com.abberadhi.mc_forum.dto;

import java.time.LocalDateTime;

import com.abberadhi.mc_forum.model.BikeModelEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    private Integer id;
    private String username;
    private String description;
    private LocalDateTime dateJoined;
    private BikeModelEntity bike;
}
