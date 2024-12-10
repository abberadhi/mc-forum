package com.abberadhi.mc_forum.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.abberadhi.mc_forum.model.BikeModelEntity;
import com.abberadhi.mc_forum.model.CommentEntity;
import com.abberadhi.mc_forum.model.PostEntity;
import com.abberadhi.mc_forum.model.TagEntity;
import com.abberadhi.mc_forum.model.UserEntity;
import com.abberadhi.mc_forum.repository.BikeModelRepository;
import com.abberadhi.mc_forum.repository.CommentRepository;
import com.abberadhi.mc_forum.repository.PostRepository;
import com.abberadhi.mc_forum.repository.TagRepository;
import com.abberadhi.mc_forum.repository.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final BikeModelRepository bikeModelRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final CsvUtils csvUtils;

    @Override
    public void run(String... args) throws Exception {
        String bikesFile = loadFilePath("data/bikes-list.csv");
        String usersFile = loadFilePath("data/users-list.csv");
        String tagsFile = loadFilePath("data/tag-list.csv");
        String postsFile = loadFilePath("data/post-list.csv");
        String commentsFile = loadFilePath("data/comments-list.csv");

        populateBikeTable(bikesFile);
        populateUserTable(usersFile);
        populateTagsTable(tagsFile);
        populatePostsTable(postsFile);
        populateCommentsTable(commentsFile);
    }

    private String loadFilePath(String resourcePath) throws Exception {
        try (InputStream inputStream = new ClassPathResource(resourcePath).getInputStream()) {
            // Create a temporary file to work with the resource
            File tempFile = File.createTempFile("resource", null);
            tempFile.deleteOnExit();

            // Copy the resource content to the temporary file
            try (FileOutputStream out = new FileOutputStream(tempFile)) {
                inputStream.transferTo(out);
            }
            return tempFile.getAbsolutePath();
        }
    }

    private void populateBikeTable(String filePath) {
        List<BikeModelEntity> bikeEntities = csvUtils.readBikesFromCsv(filePath);

        if (bikeModelRepository.count() != 0) {
            return;
        }

        for (BikeModelEntity bike : bikeEntities) {
            bikeModelRepository.save(bike);
        }
    }

    private void populateUserTable(String filePath) {
        if (userRepository.count() != 0) {
            return;
        }

        List<UserEntity> userEntities = csvUtils.readUsersFromCsv(filePath);
        userEntities.forEach(userRepository::save);
    }

    private void populateTagsTable(String filePath) {
        if (tagRepository.count() != 0) {
            return;
        }

        List<TagEntity> tagEntities = csvUtils.readTagsFromCsv(filePath);
        tagEntities.forEach(tagRepository::save);
    }

    private void populatePostsTable(String filePath) {
        if (postRepository.count() != 0) {
            return;
        }

        List<PostEntity> postEntities = csvUtils.readPostsFromCsv(filePath);
        postEntities.forEach(postRepository::save);
    }

    private void populateCommentsTable(String filePath) {
        if (commentRepository.count() != 0) {
            return;
        }

        List<CommentEntity> commentEntities = csvUtils.readCommentsFromCsv(filePath);
        // commentEntities.forEach(commentRepository::save);
    }
}
