package com.example.travelly.Controller;

import com.example.travelly.Dto.Postdto;
import com.example.travelly.Dto.Userdto;
import com.example.travelly.Model.Image;
import com.example.travelly.Model.Posts;
import com.example.travelly.Repository.ImageRepo;
import com.example.travelly.Repository.PostsRepo;
import com.example.travelly.Service.HomeServiceImpl;
import com.example.travelly.Service.PostServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/navbar")
@Slf4j
@AllArgsConstructor
public class NavbarController {
    private HomeServiceImpl homeService;

    // will get suggested users
    @GetMapping("/suggestedUsers")
    public ResponseEntity getSuggestedUsers() {
        log.info("*** Inside getSuggestedUsers Controller ***");
        List<Userdto> listOfUsers = homeService.getSuggestedUsers();
        return new ResponseEntity(listOfUsers, HttpStatus.OK);
    }

    // will get the post on home page
    @GetMapping("/home")
    public ResponseEntity getHomeData(@RequestParam(required = false) String orderBy) {
        log.info("*** getHomeData Controller ***");
        List<Postdto> posts = homeService.getAllMyFollowersAndFollowingPosts(orderBy);
        return new ResponseEntity(posts, HttpStatus.OK);
    }

    // will get explore data
    @GetMapping("/explore")
    public ResponseEntity getExploreData() {
        return null;
    }

    //will get bookmark data
    @GetMapping("/bookmark")
    public ResponseEntity getBookMarkData() {
        return null;
    }

}
