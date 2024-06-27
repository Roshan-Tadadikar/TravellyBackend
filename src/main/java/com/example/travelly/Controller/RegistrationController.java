package com.example.travelly.Controller;

import com.example.travelly.Dto.RegistrationRequest;
import com.example.travelly.Exceptions.CustomizedException;
import com.example.travelly.Repository.UserRepo;
import com.example.travelly.Service.RegistrationServiceImpl;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    UserRepo userRepo;

    @Autowired
    RegistrationServiceImpl registrationService;

    @PostMapping
    public ResponseEntity registration(@RequestBody @Valid RegistrationRequest newRegistration){
        log.info("**** Inside registration controller ****");
        Map<String, String> map = new HashMap<>();
        String token = registrationService.registeredUser(newRegistration);
        map.put("Message", " User registered successfully, use this token: "+token+ " to activate the same");
        return new ResponseEntity(map, HttpStatus.CREATED);
    }

    @GetMapping("/{token}")
    public String activateRegisteredUser(@PathVariable("token") String token){
        log.info("*** Inside activateRegisteredUser controller ***");
        if (token == null) {
            throw new CustomizedException("Message", "Invalid User");
        }
        registrationService.activateRegisteredUser(token);
        return "User activated successfully!";
    }

    @GetMapping("/reset")
    public ResponseEntity resetPassword(@RequestParam String username){
        log.info("*** Inside reset Password Controller ***");
        String token = registrationService.resetPassword(username);
        return new ResponseEntity(token, HttpStatus.CREATED);
    }

    @GetMapping("/reset/{token}")
    public ResponseEntity resetPassword(@RequestParam String password, @RequestParam String confirmPassword,
                                        @PathVariable String token){
        log.info("*** Inside resetPassword Controller ***");
        registrationService.resetPassword(token, password, confirmPassword);
        return new ResponseEntity("Password changed successfully, kindly login !", HttpStatus.OK);
    }


}
