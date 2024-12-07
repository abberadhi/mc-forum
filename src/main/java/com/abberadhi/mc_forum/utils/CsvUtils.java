package com.abberadhi.mc_forum.utils;

import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
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
import com.opencsv.CSVReader;

@Component
public class CsvUtils {

    private final PasswordEncoder passwordEncoder;
    private final BikeModelRepository bikeModelRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final TagRepository tagRepository;

    public CsvUtils(
            PasswordEncoder passwordEncoder,
            BikeModelRepository bikeModelRepository,
            UserRepository userRepository,
            TagRepository tagRepository,
            PostRepository postRepository,
            CommentRepository commentRepository) {
        this.passwordEncoder = passwordEncoder;
        this.bikeModelRepository = bikeModelRepository;
        this.userRepository = userRepository;
        this.tagRepository = tagRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    public List<BikeModelEntity> readBikesFromCsv(String filePath) {
        List<BikeModelEntity> bikes = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                BikeModelEntity bike = new BikeModelEntity();
                bike.setBrand(line[0].strip());
                bike.setModel(line[1].strip());
                bikes.add(bike);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bikes;
    }

    public List<UserEntity> readUsersFromCsv(String filePath) {
        List<UserEntity> users = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                UserEntity u = new UserEntity();
                u.setUsername(line[1].strip());
                u.setDescription(line[2].strip());
                u.setDateJoined(LocalDateTime.now());
                u.setPassword(passwordEncoder.encode("pass"));
                Long bikeModelId = Long.parseLong(line[5]);
                u.setBikeModelEntity(bikeModelRepository.findById(bikeModelId).orElse(null));
                users.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public List<TagEntity> readTagsFromCsv(String filePath) {
        List<TagEntity> tags = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                TagEntity t = new TagEntity();
                t.setName(line[0]);
                tags.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tags;
    }

    public List<PostEntity> readPostsFromCsv(String filePath) {
        List<PostEntity> posts = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                PostEntity p = new PostEntity();
                p.setTitle(line[0]);
                p.setContent(line[1]);
                p.setUser(userRepository.findById(Integer.parseInt(line[2])).orElse(null));
                Set<TagEntity> tags = new HashSet<>();
                String[] tagNames = line[3].split(";");
                for (String tag : tagNames) {
                    TagEntity tagEntity = tagRepository.findByName(tag).orElse(null);
                    if (tagEntity == null) {
                        continue;
                    }
                    tags.add(tagEntity);
                }
                p.setTags(tags);
                posts.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return posts;
    }

    public List<CommentEntity> readCommentsFromCsv(String filePath) {
        List<CommentEntity> comments = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                CommentEntity comment = new CommentEntity();
                comment.setCommentContent(line[0].strip());
                comment.setPostEntity(postRepository.findById(Long.parseLong(line[1])).orElse(null));
                comment.setUserEntity(userRepository.findById(Integer.parseInt(line[2])).orElse(null));

                // Handle parent comment reference
                if (line[3] != null && !line[3].isEmpty()) {
                    comment.setParentCommentEntity(commentRepository.findById(Long.parseLong(line[3])).orElse(null));
                }

                // Handle upvote count
                comment.setUpvoteCount(Integer.parseInt(line[4].strip()));

                commentRepository.save(comment);
                comments.add(comment);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return comments;
    }

}
