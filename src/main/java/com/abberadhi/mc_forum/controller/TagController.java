package com.abberadhi.mc_forum.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abberadhi.mc_forum.model.TagEntity;
import com.abberadhi.mc_forum.service.PostService;
import com.abberadhi.mc_forum.service.TagService;



@RestController
@RequestMapping("/api/tags")
public class TagController {

    private final PostService postService;
    private final TagService tagService;

    public TagController(TagService tagService, PostService postService) {
        this.postService = postService;
        this.tagService = tagService;
    }

    @GetMapping
    public ResponseEntity<List<TagEntity>> getAllTags() {
        List<TagEntity> tags = tagService.getAllTags();
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }
}
