package com.example.travelly.Controller;

import com.example.travelly.Dto.Logindto;
import com.example.travelly.Service.RegistrationServiceImpl;
import com.example.travelly.Service.LoginServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/login")
@Slf4j
@RestController
public class LoginController {

    @Autowired
    LoginServiceImpl loginService;

    @Autowired
    RegistrationServiceImpl registrationService;

    @PostMapping
    public ResponseEntity login(@RequestBody Logindto request){
        log.info("*** Inside login controller ***");
        String viewName =  loginService.login(request);
        return new ResponseEntity(viewName, HttpStatus.OK);
    }


}
