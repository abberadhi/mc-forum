package com.abberadhi.mc_forum.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abberadhi.mc_forum.model.CommentEntity;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    
    // Custom query methods can be defined here
    Optional<CommentEntity> findById(int id);
}
