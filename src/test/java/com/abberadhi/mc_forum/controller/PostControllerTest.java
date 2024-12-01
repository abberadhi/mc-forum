package com.abberadhi.mc_forum.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.event.annotation.BeforeTestExecution;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.abberadhi.mc_forum.model.PostEntity;
import com.abberadhi.mc_forum.model.RegisterRequest;
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

    private String jwtToken;

	@BeforeEach
    public void setUp() throws Exception {
        jdbcTemplate.execute("TRUNCATE TABLE post_entity RESTART IDENTITY CASCADE");
        jdbcTemplate.execute("TRUNCATE TABLE user_entity RESTART IDENTITY CASCADE");
        // Register a user
        RegisterRequest newUser = new RegisterRequest();
        newUser.setUsername("user1337");
        newUser.setPassword("user1337");

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isOk());

        String loginResponse = mockMvc.perform(post("/api/auth/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        jwtToken = objectMapper.readTree(loginResponse).get("token").asText();
    }

    @Test
    public void createPost_ShouldReturnCreatedPost() throws Exception {
        PostEntity post = new PostEntity();
        post.setTitle("Sample Title");
        post.setContent("Sample Content");

        // Perform POST request to create the post
        mockMvc.perform(post("/api/posts")
                .header("Authorization", "Bearer " + jwtToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(post)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists()) // Ensure ID is returned
                .andExpect(jsonPath("$.title").value(post.getTitle()))
                .andExpect(jsonPath("$.content").value(post.getContent()));
    }

    @Test
    public void getAllPost_ShouldReturnCreatedPost() throws Exception {
        PostEntity post = new PostEntity();
        post.setTitle("Sample Title");
        post.setContent("Sample Content");

        mockMvc.perform(post("/api/posts")
            .header("Authorization", "Bearer " + jwtToken)
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(post)));

		PostEntity post2 = new PostEntity();
        post2.setTitle("Sample Title2");
        post2.setContent("Sample Content2");

        mockMvc.perform(post("/api/posts")
                .header("Authorization", "Bearer " + jwtToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(post2)));

        mockMvc.perform(get("/api/posts").header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
				.andExpect(jsonPath("$.[0].id").value(1))
                .andExpect(jsonPath("$.[1].id").value(2))
                .andExpect(jsonPath("$.[0].title").value(post.getTitle()))
                .andExpect(jsonPath("$.[1].title").value(post2.getTitle()))
                .andExpect(jsonPath("$.[0].content").value(post.getContent()))
                .andExpect(jsonPath("$.[1].content").value(post2.getContent()));
    }

    @Test
    public void getPostById() throws Exception {
        PostEntity post = new PostEntity();
        post.setTitle("my Post");
        post.setContent("Sample Content");

        mockMvc.perform(post("/api/posts")
            .header("Authorization", "Bearer " + jwtToken)
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(post)));

        mockMvc.perform(get("/api/posts/1").header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value(post.getTitle()))
                .andExpect(jsonPath("$.content").value(post.getContent()));
    }

    @Test
    @BeforeTestExecution()
    public void deletePostById() throws Exception {
        PostEntity post = new PostEntity();
        post.setTitle("my Post");
        post.setContent("Sample Content");

        mockMvc.perform(post("/api/posts")
            .header("Authorization", "Bearer " + jwtToken)
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(post)));

        mockMvc.perform(delete("/api/posts/1").header("Authorization", "Bearer " + jwtToken))
            .andExpect(status().isNoContent());
    }

    @Test
    @BeforeTestExecution()
    public void updatePostById() throws Exception {
        PostEntity initialPost = new PostEntity();
        initialPost.setTitle("my Post");
        initialPost.setContent("Sample Content");

        mockMvc.perform(post("/api/posts")
            .header("Authorization", "Bearer " + jwtToken)
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(initialPost)));

        // updating title
        PostEntity updatePostTitle = new PostEntity();
        updatePostTitle.setTitle("my Post");
        
        mockMvc.perform(patch("/api/posts/1")
            .header("Authorization", "Bearer " + jwtToken)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updatePostTitle)))
            .andExpect(status().isAccepted());

        mockMvc.perform(get("/api/posts/1").header("Authorization", "Bearer " + jwtToken))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.title").value(updatePostTitle.getTitle()))
            .andExpect(jsonPath("$.content").value(initialPost.getContent()));

        // updating content
        PostEntity updateContentTitle = new PostEntity();
        updateContentTitle.setContent("updated content");

        mockMvc.perform(patch("/api/posts/1")
            .header("Authorization", "Bearer " + jwtToken)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updateContentTitle)))
            .andExpect(status().isAccepted());

        mockMvc.perform(get("/api/posts/1").header("Authorization", "Bearer " + jwtToken))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.title").value(updatePostTitle.getTitle()))
            .andExpect(jsonPath("$.content").value(updateContentTitle.getContent()));
    }
}
