package com.abberadhi.mc_forum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abberadhi.mc_forum.model.PostEntity;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
    
    // Custom query methods can be defined here

    List<PostEntity> findByUserId(Long userId); // Find posts by user ID

    List<PostEntity> findByTitleContaining(String title); // Find posts by title containing a keyword

    // Add more query methods as needed
}
