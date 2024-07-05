package com.example.travelly.Controller;

import com.example.travelly.Service.FollowUnfollowUserServiceImpl;
import com.example.travelly.Service.HomeServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/follow")
public class FollowUserController {

    @Autowired
    FollowUnfollowUserServiceImpl followUnfollowUser;

    @PostMapping("/{userId}")
    public ResponseEntity followUser(@PathVariable String userId){
        log.info("**** Inside followUser ****");
        String message = followUnfollowUser.followUnfollowUser(userId);
        return new ResponseEntity(message, HttpStatus.OK);
    }
}
