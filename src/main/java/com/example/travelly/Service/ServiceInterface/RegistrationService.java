package com.example.travelly.Service.ServiceInterface;


import com.example.travelly.Dto.RegistrationRequest;

public interface RegistrationService {

     String registeredUser(RegistrationRequest newUser);

     void activateRegisteredUser(String token);

}
