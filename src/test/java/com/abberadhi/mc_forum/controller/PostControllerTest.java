package com.abberadhi.mc_forum.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.abberadhi.mc_forum.model.PostEntity;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private ObjectMapper objectMapper;

	@Autowired
    private JdbcTemplate jdbcTemplate;

	@BeforeEach
    public void cleanUp() {
        jdbcTemplate.execute("TRUNCATE TABLE post_entity RESTART IDENTITY CASCADE");
    }

    @Test
    public void createPost_ShouldReturnCreatedPost() throws Exception {
        PostEntity post = new PostEntity();
        post.setTitle("Sample Title");
        post.setContent("Sample Content");

        // Perform POST request to create the post
        mockMvc.perform(post("/api/posts") 
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(post)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists()) // Ensure ID is returned
                .andExpect(jsonPath("$.title").value(post.getTitle()))
                .andExpect(jsonPath("$.content").value(post.getContent()))
                .andDo(print());
    }

    @Test
    public void getAllPost_ShouldReturnCreatedPost() throws Exception {
		// post1
        PostEntity post = new PostEntity();
        post.setTitle("Sample Title");
        post.setContent("Sample Content");

        mockMvc.perform(post("/api/posts") 
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(post)));

		// post 2
		PostEntity post2 = new PostEntity();
        post2.setTitle("Sample Title2");
        post2.setContent("Sample Content2");

        mockMvc.perform(post("/api/posts") 
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(post2)));


        // Perform GET request to fetch all posts and check if the post is returned
        mockMvc.perform(get("/api/posts"))
                .andExpect(status().isOk())
				.andExpect(jsonPath("$.[0].id").value(1))
                .andExpect(jsonPath("$.[1].id").value(2))
                .andExpect(jsonPath("$.[0].title").value(post.getTitle()))
                .andExpect(jsonPath("$.[1].title").value(post2.getTitle()))
                .andExpect(jsonPath("$.[0].content").value(post.getContent()))
                .andExpect(jsonPath("$.[1].content").value(post2.getContent()))
                .andDo(print());
    }
}
