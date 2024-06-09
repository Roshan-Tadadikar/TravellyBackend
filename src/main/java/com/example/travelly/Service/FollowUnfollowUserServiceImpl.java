package com.example.travelly.Service;

import com.example.travelly.Exceptions.CustomizedException;
import com.example.travelly.Repository.UserRepo;
import com.example.travelly.Service.ServiceInterface.FollowUnfollowUserService;
import com.example.travelly.Utility.Validations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class FollowUnfollowUserServiceImpl implements FollowUnfollowUserService {

    @Autowired
    UserRepo userRepo;


    @Override
    public ResponseEntity followUser(String email) {
//        if(!validations.validEmail(email)){
//            throw new CustomizedException("Message","Invalid email address or user not found");
//        }

    return null;

    }

    @Override
    public ResponseEntity unfollowUser(String email) {
        return null;
    }
}
