package com.example.travelly.Service;

import com.example.travelly.Dto.RegistrationRequest;
import com.example.travelly.Exceptions.CustomizedException;
import com.example.travelly.Model.Register;
import com.example.travelly.Model.Token;
import com.example.travelly.Model.User;
import com.example.travelly.Repository.RegistrationRepo;
import com.example.travelly.Repository.TokenRepo;
import com.example.travelly.Repository.UserRepo;
import com.example.travelly.Service.ServiceInterface.RegistrationService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RegistrationRepo registerRepo;

    @Autowired
    private TokenRepo tokenRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public String registeredUser(RegistrationRequest newUser) {
        Optional<User> ifUserExists = userRepo.findByEmail(newUser.getEmail());
        if (ifUserExists.isPresent()) {
            throw new CustomizedException("Message", "User " + newUser.getEmail() + " already exits kindly login !");
        }

        Register registerUser = new Register().builder()
                .name(newUser.getFullName())
                .email(newUser.getEmail())
                .password(passwordEncoder.encode(newUser.getPassword()))
                .createdAt(LocalDateTime.now())
                .build();
        Register registeredUser = registerRepo.save(registerUser);
        final String token = UUID.randomUUID().toString();
        Token newToken = new Token().builder()
                .token(token)
                .user(registeredUser)
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusMinutes(15))
                .build();
        tokenRepo.save(newToken);
        return token;
    }

    @Override
    @Transactional
    public void activateRegisteredUser(String token) {
        Optional<Token> foundToken = tokenRepo.findByToken(token);
        if (!foundToken.isPresent()) {
            throw new CustomizedException("Message", "Invalid token !");
        }

        if (LocalDateTime.now().isAfter(foundToken.get().getExpiredAt())) {
            throw new CustomizedException("Message", "Token has expired kindly register again !");
        }
        Register registeredUser = foundToken.get().getUser();

        User newUser = new User().builder()
                .createdAt(LocalDateTime.now())
                .email(registeredUser.getEmail())
                .name(registeredUser.getEmail())
                .build();
        userRepo.save(newUser);
    }
}
