package com.abberadhi.mc_forum.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.abberadhi.mc_forum.model.PostEntity;
import com.abberadhi.mc_forum.model.TagEntity;
import com.abberadhi.mc_forum.repository.PostRepository;
import com.abberadhi.mc_forum.repository.TagRepository;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final int PAGESIZE = 10;

    @Autowired
    public PostService(PostRepository postRepository, TagRepository tagRepository) {
        this.postRepository = postRepository;
        this.tagRepository = tagRepository;
    }

    public List<PostEntity> getAllPosts(int pageNumber, String postTitle, String tagName) {
        Pageable page = PageRequest.of(pageNumber, PAGESIZE, Sort.by("title")); // TODO: sort by date
        Page<PostEntity> result =  postRepository.findAllWithFilters(postTitle, tagName, page);

        return result.toList();
    }

    public PostEntity getPostById(Long id) {
        return postRepository.findById(id).orElse(null); // Optional handling
    }

    // public List<PostEntity> getPostsByUserId(Long userId) {
    //     return postRepository.findByUserId(userId);
    // }

    public PostEntity createPost(PostEntity post) {
        Set<TagEntity> tags = new HashSet<>();
        for (TagEntity tag : post.getTags()) {
            TagEntity tagEntity = tagRepository.findByName(tag.getName())
                    .orElseGet(() -> tagRepository.save(new TagEntity(tag.getName())));
            tags.add(tagEntity);
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
