package com.example.travelly.Service;

import com.example.travelly.Dto.Logindto;
import com.example.travelly.Exceptions.CustomizedException;
import com.example.travelly.Model.Register;
import com.example.travelly.Model.User;
import com.example.travelly.Repository.RegistrationRepo;
import com.example.travelly.Repository.UserRepo;
import com.example.travelly.Service.ServiceInterface.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    RegistrationRepo registrationRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public String login(Logindto request) {
       if(request == null || request.getEmail() == null
          || request.getEmail().isEmpty() || request.getPassword() == null
          || request.getPassword().isEmpty()){
           throw new CustomizedException("Error","Please enter both the fields !");
       }

        Optional<Register> userExists = registrationRepo.findByEmail(request.getEmail());
        if(!userExists.isPresent()){
            throw new CustomizedException("Message","User doesn't exist");
        }

        Register foundRegisteredUser = userExists.get();
        if(!passwordEncoder.matches(foundRegisteredUser.getPassword(), passwordEncoder.encode(request.getPassword()))){
            throw new CustomizedException("Message", "Invalid Credentials !");
        }

        return "Succesfully logged In!";
    }
}
