package com.abberadhi.mc_forum.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abberadhi.mc_forum.dto.CommentDTO;
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

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByPostId(@PathVariable Long postId) {
        PostEntity post = postService.getPostById(postId);
        if (post == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<CommentEntity> postComments = commentService.getCommentsByPostId(postId);
        List<CommentDTO> commentDTO = postComments.stream()
                .map(this::mapToCommentDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(commentDTO, HttpStatus.OK);
    }

    @PostMapping("/reply/{postId}")
    public ResponseEntity<CommentDTO> CreateCommentsByPostId(@PathVariable Long postId, @RequestBody CommentEntity comment) {
        PostEntity post = postService.getPostById(postId);

        if (post == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        CommentEntity createdComment = commentService.createComment(post, null, comment);
        CommentDTO createdCommentDTO = mapToCommentDTO(createdComment);
        return new ResponseEntity<>(createdCommentDTO, HttpStatus.OK);
    }

    @PostMapping("/reply/{postId}/{parentCommentId}")
    public ResponseEntity<CommentDTO> CreateCommentsByPostId(@PathVariable Long postId, @PathVariable Long parentCommentId, @RequestBody CommentEntity comment) {
        PostEntity post = postService.getPostById(postId);
        CommentEntity parentComment = commentService.getCommentById(parentCommentId);

        if (post == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (parentComment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        CommentEntity createdComment = commentService.createComment(post, parentComment, comment);
        CommentDTO createdCommentDTO = mapToCommentDTO(createdComment);

        return new ResponseEntity<>(createdCommentDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommentById(@PathVariable Long id) {
        CommentEntity comment = commentService.getCommentById(id);
        if (comment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        commentService.deleteCommentById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<Void> likeComment(@PathVariable Long id) {
        commentService.likeComment(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{id}/unlike")
    public ResponseEntity<Void> unlikeComment(@PathVariable Long id) {
        commentService.unlikeComment(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public CommentDTO mapToCommentDTO(CommentEntity commentEntity) {
        if (commentEntity == null) {
            return null;
        }

        CommentDTO dto = new CommentDTO();
        dto.setId(commentEntity.getId());
        dto.setCommentContent(commentEntity.getCommentContent());
        dto.setCreatedAt(commentEntity.getCreatedAt());
        dto.setUpvoteCount(commentService.getLikeCountByCommentId(commentEntity.getId()));
        dto.setUser_id(commentEntity.getUserEntity().getId());
        dto.setUsername(commentEntity.getUserEntity().getUsername());
        dto.setHasLiked(commentService.authUserHasLikedComment(commentEntity.getId()));

        if (commentEntity.getParentCommentEntity() != null) {
            dto.setParent_id(commentEntity.getParentCommentEntity().getId());
        }
        return dto;
    }
}
