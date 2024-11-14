package com.abberadhi.mc_forum.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abberadhi.mc_forum.model.TagEntity;
import com.abberadhi.mc_forum.repository.PostRepository;
import com.abberadhi.mc_forum.repository.TagRepository;

@Service
public class TagService {

    private final PostRepository postRepository;
    private final TagRepository tagRepository;

    @Autowired
    public TagService(PostRepository postRepository, TagRepository tagRepository) {
        this.postRepository = postRepository;
        this.tagRepository = tagRepository;
    }

    public List<TagEntity> getAllTags() {
        return tagRepository.findAll();
    }

    public TagEntity getTagById(Long id) {
        return tagRepository.findById(id).orElse(null); // Optional handling
    }

    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }
}
