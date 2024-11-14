package com.abberadhi.mc_forum.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abberadhi.mc_forum.model.PostEntity;
import com.abberadhi.mc_forum.service.PostService;


@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<PostEntity>> getAllPosts() {
        List<PostEntity> posts = postService.getAllPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostEntity> getPostById(@PathVariable Long id) {
        PostEntity post = postService.getPostById(id);
        return post != null ? new ResponseEntity<>(post, HttpStatus.OK) :
                              new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // @GetMapping("/user/{userId}")
    // public ResponseEntity<List<PostEntity>> getPostsByUserId(@PathVariable Long userId) {
    //     List<PostEntity> posts = postService.getPostsByUserId(userId);
    //     return new ResponseEntity<>(posts, HttpStatus.OK);
    // }

    @PostMapping
    public ResponseEntity<PostEntity> createPost(@RequestBody PostEntity post) {
        PostEntity createdPost = postService.createPost(post);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        PostEntity post = postService.getPostById(id);
        if (post == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        postService.deletePost(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePost(@PathVariable Long id, @RequestBody PostEntity post) {
        postService.updatePost(id, post);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
