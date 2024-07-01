package com.example.travelly.Service;

import com.example.travelly.Dto.Postdto;
import com.example.travelly.Exceptions.CustomizedException;
import com.example.travelly.Model.Image;
import com.example.travelly.Model.Posts;
import com.example.travelly.Model.User;
import com.example.travelly.Repository.ImageRepo;
import com.example.travelly.Repository.PostsRepo;
import com.example.travelly.Service.ServiceInterface.PostService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    private CustomUserDetailsServiceImpl customUserDetailsService;
    private PostsRepo postsRepo;
    private ImageRepo imageRepo;
    private ModelMapper modelMapper;

    @Override
    public void toggleLike(Integer postId) {

    }

    @Override
    public void toggleBookmark(Integer postId) {

    }

    @Transactional
    @Override
    public void deletePost(Integer postId) {
        if (postId == null) {
            throw new CustomizedException("Message", "Something wen't wrong !");
        }
        User loggedInUser = customUserDetailsService.getLoggedInUserDetails();
        try {
            postsRepo.deleteByIdAndUserId(postId, loggedInUser.getId());
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomizedException("Message", e.getLocalizedMessage());
        }
    }

    @Override
    public void addComment(String comment, Integer commenterId, Integer postId) {

    }

    @Transactional
    @Override
    public void addPost(String content, List<MultipartFile> files) {
        if (content.isEmpty() || content == null) {
            throw new CustomizedException("Content", "Content cannot be empty");
        }
        User loggedInUserDetails = customUserDetailsService.getLoggedInUserDetails();
        Posts post = new Posts().builder()
                .addedTime(LocalDateTime.now())
                .content(content)
                .user(loggedInUserDetails)
                .build();
        Posts savedPost = postsRepo.save(post);
        Set<Image> images = new HashSet<>();
        try {
            if (files != null && !files.isEmpty() && files.size() > 0) {
                for (MultipartFile file : files) {
                    Image imageToBeUploaded = new Image();
                    imageToBeUploaded.setName(file.getName());
                    imageToBeUploaded.setContent(file.getBytes());
                    imageToBeUploaded.setAddedTime(LocalDateTime.now());
                    imageToBeUploaded.setPosts(savedPost);
                    Image savedImage = imageRepo.save(imageToBeUploaded);
                    images.add(savedImage);
                }
            }
        } catch (Exception e) {
            throw new CustomizedException("Message", e.getLocalizedMessage());
        }
    }


    @Transactional
    @Override
    public Postdto updatePost(String updatedContent, Integer postId, List<MultipartFile> files) {
        if ((updatedContent == null || updatedContent.isEmpty()) && (files == null || files.isEmpty())) {
            throw new CustomizedException("Request", "Invalid request!");
        }

        if (postId == null) {
            throw new CustomizedException("Message", "Invalid creds!");
        }

        Optional<Posts> postsExists = postsRepo.findById(postId);
        if (!postsExists.isPresent()) {
            throw new CustomizedException("Message", "Invalid creds!");
        }

        User loggedInUser = customUserDetailsService.getLoggedInUserDetails();

        Posts foundPost = postsExists.get();

        if (updatedContent != null && !updatedContent.isEmpty()) {
            foundPost.setContent(updatedContent);
        }
        Set<Image> updatedImages = new HashSet<>();
        try {
            if (files != null && !files.isEmpty() && files.size() > 0) {
                postsRepo.deleteByIdAndUserId(foundPost.getId(), loggedInUser.getId()); // delete prev images
                for (MultipartFile file : files) {
                    Image updatedImage = new Image();
                    updatedImage.builder()
                            .addedTime(LocalDateTime.now())
                            .content(file.getBytes())
                            .name(file.getName())
                            .posts(foundPost)
                            .build();
                    Image savedImage = imageRepo.save(updatedImage);
                    updatedImages.add(savedImage);
                }
            }
        } catch (Exception e) {
            throw new CustomizedException("Image", e.getLocalizedMessage());
        }

        foundPost.setUpdatedTime(LocalDateTime.now());
        Posts savedPost = postsRepo.save(foundPost);

        return modelMapper.map(savedPost, Postdto.class);
    }

}
