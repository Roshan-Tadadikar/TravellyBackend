package com.example.travelly.Service.ServiceInterface;

import com.example.travelly.Dto.Postdto;
import com.example.travelly.Dto.Userdto;
import com.example.travelly.Model.Posts;

import java.util.List;

public interface HomeService {

    List<Postdto> getAllMyFollowersAndFollowingPosts(Integer userId);
    List<Userdto> getSuggestedUsers();
}
