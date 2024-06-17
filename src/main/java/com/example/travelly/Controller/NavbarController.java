package com.example.travelly.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/navbar")
public class NavbarController {

    @GetMapping("/suggestedUsers")
    public ResponseEntity getSuggestedUsers(){
        return  null;
    }


    @GetMapping("/home")
    public ResponseEntity getHomeData(){
        return  null;
    }

    @GetMapping("/explore")
    public ResponseEntity getExploreData(){
        return  null;
    }

    @GetMapping("/bookmark")
    public ResponseEntity getBookMarkData(){
        return  null;
    }
}
