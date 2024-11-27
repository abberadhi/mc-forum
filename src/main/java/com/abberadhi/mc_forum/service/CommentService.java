package com.abberadhi.mc_forum.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abberadhi.mc_forum.model.CommentEntity;
import com.abberadhi.mc_forum.model.PostEntity;
import com.abberadhi.mc_forum.model.TagEntity;
import com.abberadhi.mc_forum.repository.CommentRepository;
import com.abberadhi.mc_forum.repository.PostRepository;
import com.abberadhi.mc_forum.repository.TagRepository;

@Service
public class CommentService {

    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(PostRepository postRepository, TagRepository tagRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.tagRepository = tagRepository;
        this.commentRepository = commentRepository;
    }

    public List<TagEntity> getAllTags() {
        return tagRepository.findAll();
    }

    public CommentEntity createComment(PostEntity post, CommentEntity parentComment, CommentEntity comment) {
        comment.setParentCommentEntity(parentComment);
        comment.setPostEntity(post);

        return commentRepository.save(comment);
    }
}
