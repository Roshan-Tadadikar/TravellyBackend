package com.example.travelly.Service;

import com.example.travelly.Exceptions.CustomizedException;
import com.example.travelly.Model.Followers;
import com.example.travelly.Model.User;
import com.example.travelly.Repository.FollowersRepo;
import com.example.travelly.Repository.UserRepo;
import com.example.travelly.Service.ServiceInterface.FollowUnfollowUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@AllArgsConstructor
public class FollowUnfollowUserServiceImpl implements FollowUnfollowUserService {


    UserRepo userRepo;
    FollowersRepo followersRepo;
    CustomUserDetailsServiceImpl customUserDetailsService;

    @Override
    public String followUnfollowUser(String userId) {
        String message = "Now you're following "+userId+" !";
        if (userId == null || userId.isEmpty()) {
            throw new CustomizedException("Message", "Invalid user !");
        }

        User loggedInUser = customUserDetailsService.getLoggedInUserDetails();

        if(loggedInUser.getUsername().equals(userId)){
            throw new CustomizedException("Message","You can't follow yourself !");
        }

        User userToBeFollowed = null;
        try {
            Optional<User> foundUser = userRepo.findByEmail(userId);
            if (foundUser != null && foundUser.isPresent()) {
                userToBeFollowed = foundUser.get();
            }
        } catch (Exception e) {
            throw new CustomizedException("Error", "User doesn't exist !");
        }

        Integer followExistsId = followersRepo.findByCurrentUserIdAndFollowingId(loggedInUser.getId(), userToBeFollowed.getId());
        if (followExistsId != null && followExistsId > 0) {
            followersRepo.deleteById(followExistsId);
            message = "You unfollowed "+userId+" !";
        } else {
            Followers followingUser = new Followers()
                    .builder()
                    .user(loggedInUser)
                    .following(userToBeFollowed)
                    .createdTime(LocalDateTime.now())
                    .build();
            followersRepo.save(followingUser);
        }

        return message;
    }
}
