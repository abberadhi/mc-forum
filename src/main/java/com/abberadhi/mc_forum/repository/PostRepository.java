package com.abberadhi.mc_forum.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abberadhi.mc_forum.model.PostEntity;
import com.abberadhi.mc_forum.model.UserEntity;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {

    // Custom query methods can be defined here
    // List<PostEntity> findByUserId(Long userId); // Find posts by user ID
    List<PostEntity> findByTitleContaining(String title); // Find posts by title containing a keyword

    List<PostEntity> findByTags_name(String tagName);

    @Query("SELECT DISTINCT p FROM PostEntity p "
            + "LEFT JOIN p.tags t "
            + "WHERE (:tagName IS NULL OR t.name = :tagName) "
            + "AND (:postTitle IS NULL OR LOWER(CAST(p.title AS string)) LIKE LOWER(CONCAT('%',  CAST(:postTitle AS string), '%'))) "
            + "AND (:userEntity IS NULL OR p.userEntity = :userEntity)")

    Page<PostEntity> findAllWithFilters(@Param("postTitle") String postTitle, @Param("tagName") String tagName, @Param("userEntity") UserEntity userEntity, Pageable pageable);

    // Add more query methods as needed
}
