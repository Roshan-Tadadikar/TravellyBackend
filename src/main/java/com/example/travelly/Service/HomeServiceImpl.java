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
        Pageable pageable = PageRequest.of(0, 10,
                actualOrderBy ? Sort.by("addedTime").ascending() : Sort.by("addedTime").descending());
        User loggedInUser = customUserDetailsService.getLoggedInUserDetails();
        List<Posts> list = postsRepo.findAllByUserId(loggedInUser.getId(), pageable);
        List<Postdto> requiredList = new ArrayList<>();
        for (Posts post : list) {
            Postdto postdto = modelMapper.map(post, Postdto.class);
            requiredList.add(postdto);
        }

        return requiredList;
    }

    @Override
    public List<Userdto> getSuggestedUsers() {
        Pageable pageable = PageRequest.of(0, 5);
        List<User> listOfUsers = userRepo.findAllLatestUsers(pageable);
        List<Userdto> requiredListOfUsers = new ArrayList<>();
        for (User user : listOfUsers) {
            requiredListOfUsers.add(modelMapper.map(user, Userdto.class));
        }

        return requiredListOfUsers;
    }

}
