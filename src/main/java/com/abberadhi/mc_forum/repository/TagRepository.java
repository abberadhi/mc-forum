package com.abberadhi.mc_forum.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abberadhi.mc_forum.model.TagEntity;

@Repository
public interface TagRepository extends JpaRepository<TagEntity, Long> {
    
    // Custom query methods can be defined here
    Optional<TagEntity> findByName(String tagName);
}
