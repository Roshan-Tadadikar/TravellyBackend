package com.example.travelly.Controller;

import com.example.travelly.Dto.Postdto;
import com.example.travelly.Dto.Userdto;
import com.example.travelly.Service.HomeServiceImpl;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/navbar")
@Slf4j
public class NavbarController {

    @Autowired
    HomeServiceImpl homeService;

    // will get suggested users
    @GetMapping("/suggestedUsers")
    public ResponseEntity getSuggestedUsers(){
        log.info("*** Inside getSuggestedUsers Controller ***");
        List<Userdto> listOfUsers = homeService.getSuggestedUsers();
        return new ResponseEntity(listOfUsers, HttpStatus.OK);
    }


    // will get the post on home page
    @GetMapping("/home")
    public ResponseEntity getHomeData(@RequestParam(required = false) String orderBy){
        log.info("*** getHomeData Controller ***");
        List<Postdto> posts = homeService.getAllMyFollowersAndFollowingPosts(orderBy);
        return new ResponseEntity(posts, HttpStatus.OK);
    }

    // will allow to add a post
    @PostMapping("/home")
    public ResponseEntity addPost(@RequestParam String content,
                                  @RequestParam List<MultipartFile> listOfFiles){
        log.info("*** Inside addPost controller ***");
        homeService.addPost(content, listOfFiles);
        return  new ResponseEntity("Post added sucessfully", HttpStatus.OK);
    }

    // will allow to update a post
    @PostMapping("/home/update/{id}")
    public ResponseEntity updatePost(@PathVariable Integer id,
                                  @RequestParam(required = false) String updatedContent,
                                  @RequestParam(required = false) List<MultipartFile> listOfFiles){
        log.info("*** Inside updatePost controller ***");
        Postdto updatedPost = homeService.updatePost(updatedContent, id, listOfFiles);
        return  new ResponseEntity(updatedPost, HttpStatus.OK);
    }

    // will get explore data
    @GetMapping("/explore")
    public ResponseEntity getExploreData(){
        return  null;
    }

    //will get bookmark data
    @GetMapping("/bookmark")
    public ResponseEntity getBookMarkData(){
        return  null;
    }
}
