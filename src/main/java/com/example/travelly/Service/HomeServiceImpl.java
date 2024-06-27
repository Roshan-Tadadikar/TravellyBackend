package com.example.travelly.Service;

import com.example.travelly.Dto.Postdto;
import com.example.travelly.Dto.Userdto;
import com.example.travelly.Exceptions.CustomizedException;
import com.example.travelly.Model.Image;
import com.example.travelly.Model.Posts;
import com.example.travelly.Model.User;
import com.example.travelly.Repository.ImageRepo;
import com.example.travelly.Repository.PostsRepo;
import com.example.travelly.Repository.UserRepo;
import com.example.travelly.Service.ServiceInterface.HomeService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class HomeServiceImpl implements HomeService {

    @Autowired
    PostsRepo postsRepo;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserRepo userRepo;

    @Autowired
    ImageRepo imageRepo;

    @Autowired
    CustomUserDetailsServiceImpl customUserDetailsService;

    @Override
    public List<Postdto> getAllMyFollowersAndFollowingPosts(String orderBy) {
        final boolean actualOrderBy = (orderBy == null || orderBy.isEmpty()) ? true : false;
        Pageable pageable = PageRequest.of(0,10,
                actualOrderBy ? Sort.by("addedTime").ascending() :  Sort.by("addedTime").descending());
        User loggedInUser = customUserDetailsService.getLoggedInUserDetails();
        List<Posts> list = postsRepo.findAllByUserId(loggedInUser.getId(), pageable);
        List<Postdto> requiredList = new ArrayList<>();
        for(Posts post : list){
            requiredList.add(modelMapper.map(post, Postdto.class));
        }

        return requiredList;
    }

    @Override
    public List<Userdto> getSuggestedUsers() {
        Pageable pageable = PageRequest.of(0,5);
        List<User> listOfUsers = userRepo.findAllLatestUsers(pageable);
        List<Userdto> requiredListOfUsers = new ArrayList<>();
        for(User user : listOfUsers){
            requiredListOfUsers.add(modelMapper.map(user, Userdto.class));
        }

        return requiredListOfUsers;
    }

    @Transactional
    @Override
    public void addPost(String content, List<MultipartFile> files) {
        if(content.isEmpty() || content == null){
            throw new CustomizedException("Content","Content cannot be empty");
        }

        Set<Image> images = new HashSet<>();
        try {
            if (files != null && !files.isEmpty() && files.size()>0) {
               for(MultipartFile file : files){
                   Image imageToBeUploaded = new Image();
                   imageToBeUploaded.setName(file.getName());
                   imageToBeUploaded.setContent(file.getBytes());
                   imageToBeUploaded.setAddedTime(LocalDateTime.now());
                   Image savedImage = imageRepo.save(imageToBeUploaded);
                   images.add(savedImage);
               }
            }
        }catch (Exception e){
            throw new CustomizedException("Message",e.getLocalizedMessage());
        }

        User loggedInUserDetails = customUserDetailsService.getLoggedInUserDetails();
        Posts post = new Posts().builder()
                .addedTime(LocalDateTime.now())
                .images(images)
                .content(content)
                .user(loggedInUserDetails)
                .build();
        postsRepo.save(post);
    }

    @Transactional
    @Override
    public Postdto updatePost(String updatedContent, Integer postId, List<MultipartFile> files) {
        if((updatedContent == null || updatedContent.isEmpty()) && (files == null || files.isEmpty())){
            throw  new CustomizedException("Request","Invalid request!");
        }

        if(postId == null){
            throw new CustomizedException("Message","Invalid creds!");
        }

        Optional<Posts> postsExists = postsRepo.findById(postId);
        if(!postsExists.isPresent()){
            throw new CustomizedException("Message", "Invalid creds!");
        }

        Posts foundPost = postsExists.get();

        if(updatedContent != null && !updatedContent.isEmpty()){
            foundPost.setContent(updatedContent);
        }
        Set<Image> updatedImages = new HashSet<>();
        try {
            if (files != null && !files.isEmpty() && files.size() > 0) {
                for (MultipartFile file : files) {
                    Image updatedImage = new Image();
                    updatedImage.builder()
                            .addedTime(LocalDateTime.now())
                            .content(file.getBytes())
                            .name(file.getName())
                            .build();
                    Image savedImage = imageRepo.save(updatedImage);
                    updatedImages.add(savedImage);
                }
            }
        }catch (Exception e){
            throw new CustomizedException("Image",e.getLocalizedMessage());
        }

        if(updatedImages.size() > 0){
            foundPost.setImages(updatedImages);
        }

        foundPost.setUpdatedTime(LocalDateTime.now());
        Posts savedPost = postsRepo.save(foundPost);

        return modelMapper.map(savedPost, Postdto.class);
    }


}
