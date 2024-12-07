package com.abberadhi.mc_forum.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abberadhi.mc_forum.dto.PostDTO;
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
    public ResponseEntity<List<PostDTO>> getAllPosts(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
            @RequestParam(value = "title", required = false, defaultValue = "null") String titleSearch,
            @RequestParam(value = "tag", required = false, defaultValue = "null") String tagSearch,
            @RequestParam(value = "username", required = false, defaultValue = "null") String userName) {

        if ("null".equals(titleSearch)) {
            titleSearch = null;
        }
        if ("null".equals(tagSearch)) {
            tagSearch = null;
        }

        pageNumber--;

        List<PostEntity> posts = postService.getAllPosts(pageNumber, titleSearch, tagSearch, userName);

        List<PostDTO> dto = posts.stream()
                .map(this::mapToPostDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id) {
        PostEntity post = postService.getPostById(id);
        PostDTO dto = mapToPostDTO(post);

        return post != null ? new ResponseEntity<>(dto, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // @GetMapping("/user/{userId}")
    // public ResponseEntity<List<PostEntity>> getPostsByUserId(@PathVariable Long userId) {
    //     List<PostEntity> posts = postService.getPostsByUserId(userId);
    //     return new ResponseEntity<>(posts, HttpStatus.OK);
    // }
    @PostMapping
    public ResponseEntity<PostDTO> createPost(@RequestBody PostEntity post) {
        PostEntity createdPost = postService.createPost(post);
        PostDTO dto = mapToPostDTO(createdPost);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
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

    public PostDTO mapToPostDTO(PostEntity postEntity) {
        if (postEntity == null) {
            return null;
        }

        PostDTO dto = new PostDTO();
        dto.setId(postEntity.getId());
        dto.setTitle(postEntity.getTitle());
        dto.setContent(postEntity.getContent());
        dto.setAuthorId(postEntity.getUser().getId());
        dto.setAuthorUserName(postEntity.getUser().getUsername());
        dto.setCreatedAt(postEntity.getCreatedAt());
        dto.setUpdatedAt(postEntity.getUpdatedAt());
        dto.setTags(postEntity.getTags());

        return dto;
    }
}
