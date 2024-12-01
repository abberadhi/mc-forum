package com.abberadhi.mc_forum.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.abberadhi.mc_forum.model.PostEntity;
import com.abberadhi.mc_forum.model.TagEntity;
import com.abberadhi.mc_forum.model.UserEntity;
import com.abberadhi.mc_forum.repository.PostRepository;
import com.abberadhi.mc_forum.repository.TagRepository;
import com.abberadhi.mc_forum.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    private final int PAGESIZE = 10;


    public List<PostEntity> getAllPosts(int pageNumber, String postTitle, String tagName) {
        Pageable page = PageRequest.of(pageNumber, PAGESIZE, Sort.by("title")); // TODO: sort by date
        Page<PostEntity> result =  postRepository.findAllWithFilters(postTitle, tagName, page);
        // System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());

        return result.toList();
    }

    public PostEntity getPostById(Long id) {
        return postRepository.findById(id).orElse(null); // Optional handling
    }

    // public List<PostEntity> getPostsByUserId(Long userId) {
    //     return postRepository.findByUserId(userId);
    // }

    public PostEntity createPost(PostEntity post) {
        // System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
        Optional<UserEntity> user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Set<TagEntity> tags = new HashSet<>();
        for (TagEntity tag : post.getTags()) {
            if ("".equals(tag.getName())) {
                continue;
            }
            TagEntity tagEntity = tagRepository.findByName(tag.getName())
                    .orElseGet(() -> tagRepository.save(new TagEntity(tag.getName())));
            tags.add(tagEntity);
        }

        // System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
        if (user.isPresent()) {
            UserEntity u = user.get();
            post.setUser(u);
        }


        post.setTags(tags);

        return postRepository.save(post);
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    public void updatePost(Long id, PostEntity updatedPost) {
        PostEntity post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found")); // fix handling

        // Update the fields
        
        if (updatedPost.getTitle() != null) {
            post.setTitle(updatedPost.getTitle());
        }

        if (updatedPost.getContent() != null) {
            post.setContent(updatedPost.getContent());
        }

        post.setUpdatedAt(LocalDateTime.now());

        postRepository.save(post);
    }
}
