package com.abberadhi.mc_forum.dto;

import java.time.LocalDateTime;
import java.util.Set;

import com.abberadhi.mc_forum.model.TagEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDTO {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Set<TagEntity> tags;
    private Integer authorId;
    private String authorUserName;
    private int commentCount;
}
