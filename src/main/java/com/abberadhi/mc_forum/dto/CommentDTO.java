package com.abberadhi.mc_forum.dto;

import java.time.LocalDateTime;

public class CommentDTO {
    private Integer id;
    private String commentContent;
    private LocalDateTime createdAt;
    private CommentDTO parentCommentEntity;
    private int upvoteCount;

    public int getUpvoteCount() {
        return upvoteCount;
    }

    public void setUpvoteCount(int upvoteCount) {
        this.upvoteCount = upvoteCount;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public CommentDTO getParentCommentEntity() {
        return parentCommentEntity;
    }

    public void setParentCommentEntity(CommentDTO parentCommentEntity) {
        this.parentCommentEntity = parentCommentEntity;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    
}