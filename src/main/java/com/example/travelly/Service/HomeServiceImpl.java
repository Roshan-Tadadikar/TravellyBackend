package com.example.travelly.Service;

import com.example.travelly.Dto.Postdto;
import com.example.travelly.Dto.Userdto;
import com.example.travelly.Model.Posts;
import com.example.travelly.Model.User;
import com.example.travelly.Repository.PostsRepo;
import com.example.travelly.Repository.UserRepo;
import com.example.travelly.Service.ServiceInterface.HomeService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@Slf4j
public class HomeServiceImpl implements HomeService {

    @Autowired
    PostsRepo postsRepo;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserRepo userRepo;

    @Override
    public List<Postdto> getAllMyFollowersAndFollowingPosts(Integer userId) {
        Pageable pageable = PageRequest.of(0,10);
        List<Posts> list = postsRepo.findAllByUserId(userId, pageable);
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
}
