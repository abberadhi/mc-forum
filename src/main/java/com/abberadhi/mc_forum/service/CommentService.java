package com.abberadhi.mc_forum.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.abberadhi.mc_forum.model.CommentEntity;
import com.abberadhi.mc_forum.model.PostEntity;
import com.abberadhi.mc_forum.model.TagEntity;
import com.abberadhi.mc_forum.model.UserEntity;
import com.abberadhi.mc_forum.repository.CommentRepository;
import com.abberadhi.mc_forum.repository.PostRepository;
import com.abberadhi.mc_forum.repository.TagRepository;
import com.abberadhi.mc_forum.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final PostService postService;
    private final TagRepository tagRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public List<TagEntity> getAllTags() {
        return tagRepository.findAll();
    }

    public CommentEntity createComment(PostEntity post, CommentEntity parentComment, CommentEntity comment) {
        comment.setParentCommentEntity(parentComment);
        comment.setPostEntity(post);

        Optional<UserEntity> user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if (user.isPresent()) {
            UserEntity u = user.get();
            comment.setUserEntity(u);
        }

        return commentRepository.save(comment);
    }

    public int getLikeCountByCommentId(Integer id) {
        return commentRepository.countLikesByCommentId(id);
    }

    public boolean authUserHasLikedComment(Integer id) {
        CommentEntity comment = commentRepository.findById(id).orElse(null);
        UserEntity user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).orElse(null);
        return user.getUserCommentLikes().contains(comment);
    }

    public void likeComment(Long id) {
        CommentEntity comment = commentRepository.findById(id).orElse(null);
        UserEntity user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).orElse(null);

        // Step 3: Check if the user already liked the post
        if (user.getUserCommentLikes().contains(comment)) {
            return;
        }

        user.getUserCommentLikes().add(comment);

        userRepository.save(user);
    }

    public void unlikeComment(Long id) {
        CommentEntity comment = commentRepository.findById(id).orElse(null);
        UserEntity user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).orElse(null);

        if (!user.getUserCommentLikes().contains(comment)) {
            return;
        }

        user.getUserCommentLikes().remove(comment);
        userRepository.save(user);
    }

    public List<CommentEntity> getCommentsByPostId(Long id) {
        PostEntity postEntity = postService.getPostById(id);
        return commentRepository.findByPostEntity(postEntity);
    }

    public CommentEntity getCommentById(Long id) {
        return commentRepository.findById(id).orElse(null);
    }

    public int getCommentCountByPost(PostEntity postEntity) {
        return commentRepository.findCountByPostEntity(postEntity);
    }

    public void deleteCommentById(Long id) {
        commentRepository.deleteById(id);
    }
}
