package com.example.travelly.Service.ServiceInterface;

import com.example.travelly.Dto.Postdto;
import com.example.travelly.Dto.Userdto;
import com.example.travelly.Model.Posts;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface HomeService {

    List<Postdto> getAllMyFollowersAndFollowingPosts(String orderBy);
    List<Userdto> getSuggestedUsers();
    void addPost(String content, List<MultipartFile> file);
    Postdto updatePost(String updatedContent, Integer postId, List<MultipartFile> files);
}
