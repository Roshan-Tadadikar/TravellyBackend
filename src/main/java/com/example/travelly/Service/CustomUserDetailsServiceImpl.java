package com.example.travelly.Service;

import com.example.travelly.Model.User;
import com.example.travelly.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> ifUserExists = repo.findByEmail(username);
        if(!ifUserExists.isPresent())
            throw new UsernameNotFoundException("username not found");
        return ifUserExists.get();
    }


    public User getLoggedInUserDetails(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.getPrincipal() instanceof User){
            User newUser = (User) authentication.getPrincipal();
            return newUser;
        }

        return null;
    }
}
