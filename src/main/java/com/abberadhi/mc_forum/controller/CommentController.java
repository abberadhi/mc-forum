package com.abberadhi.mc_forum.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abberadhi.mc_forum.model.CommentEntity;
import com.abberadhi.mc_forum.model.PostEntity;
import com.abberadhi.mc_forum.service.CommentService;
import com.abberadhi.mc_forum.service.PostService;



@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final PostService postService;
    private final CommentService commentService;

    public CommentController(CommentService commentService, PostService postService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    // @GetMapping("/{postId}")
    // public ResponseEntity<PostEntity> getCommentsByPostId(@PathVariable Long id) {
    //     PostEntity post = postService.getPostById(id);
    //     return post != null ? new ResponseEntity<>(post, HttpStatus.OK) :
    //                           new ResponseEntity<>(HttpStatus.NOT_FOUND);
    // }

    @PostMapping("/reply/{postId}")
    public ResponseEntity<CommentEntity> CreateCommentsByPostId(@PathVariable Long postId, @RequestBody CommentEntity comment) {
        PostEntity post = postService.getPostById(postId);

        if (post == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        CommentEntity createdComment = commentService.createComment(post, null, comment);

        return new ResponseEntity<>(createdComment, HttpStatus.OK);
    }
}
