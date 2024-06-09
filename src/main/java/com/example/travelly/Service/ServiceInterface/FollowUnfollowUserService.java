package com.example.travelly.Service.ServiceInterface;

import org.springframework.http.ResponseEntity;

public interface FollowUnfollowUserService {

    public ResponseEntity followUser(String email);

    public ResponseEntity unfollowUser(String email);

}
