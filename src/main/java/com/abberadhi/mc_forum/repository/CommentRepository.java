package com.abberadhi.mc_forum.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abberadhi.mc_forum.model.CommentEntity;
import com.abberadhi.mc_forum.model.PostEntity;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    // Custom query methods can be defined here
    Optional<CommentEntity> findById(int id);

    List<CommentEntity> findByPostEntity(PostEntity postEntity);

    @Query("SELECT COUNT(c) FROM CommentEntity c WHERE c.postEntity = :postEntity")
    int findCountByPostEntity(@Param("postEntity") PostEntity postEntity);

    @Query("SELECT COUNT(u) FROM UserEntity u JOIN u.userCommentLikes c WHERE c.id = :commentId")
    int countLikesByCommentId(@Param("commentId") Integer commentId);

    @Query("SELECT c FROM CommentEntity c JOIN c.userEntity u WHERE u.id = :userId ORDER BY c.createdAt DESC")
    List<CommentEntity> findAllByUserId(@Param("userId") Integer userId, Pageable pageable);
}
