package com.example.travelly.Service;

import com.example.travelly.Dto.RegistrationRequest;
import com.example.travelly.EmailSender.EmailSenderImpl;
import com.example.travelly.Exceptions.CustomizedException;
import com.example.travelly.Model.Register;
import com.example.travelly.Model.Roles;
import com.example.travelly.Model.Token;
import com.example.travelly.Model.User;
import com.example.travelly.Repository.RegistrationRepo;
import com.example.travelly.Repository.RolesRepo;
import com.example.travelly.Repository.TokenRepo;
import com.example.travelly.Repository.UserRepo;
import com.example.travelly.Service.ServiceInterface.RegistrationService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private UserRepo userRepo;
    private RegistrationRepo registerRepo;
    private TokenRepo tokenRepo;
    private PasswordEncoder passwordEncoder;
    private final EmailSenderImpl emailSender;
    private RolesRepo rolesRepo;

//    @Value("${current-env-link}")
//    String currentLink;

    @Override
    @Transactional
    public String registeredUser(RegistrationRequest newUser) {
        Optional<User> ifUserExists = userRepo.findByEmail(newUser.getEmail());
        if (ifUserExists.isPresent()) {
            throw new CustomizedException("Message", "User " + newUser.getEmail() + " already exits kindly login !");
        }
        Optional<Register> alreadyRegistered = registerRepo.findByEmail(newUser.getEmail());
        Register registerUser;
        Token newToken=null;
        String token = null;
        if (alreadyRegistered.isPresent()) {
            registerUser = alreadyRegistered.get();
            registerUser.builder().createdAt(LocalDateTime.now()).build();
            Token foundToken = tokenRepo.findByUserId(registerUser.getId());
            if(foundToken.getExpiredAt().isAfter(LocalDateTime.now())){
                newToken=foundToken;
                token=foundToken.getToken();
            }
        } else {
            registerUser = new Register().builder()
                    .name(newUser.getFullName())
                    .email(newUser.getEmail())
                    .password(passwordEncoder.encode(newUser.getPassword()))
                    .createdAt(LocalDateTime.now())
                    .build();
        }

        Register registeredUser = registerRepo.save(registerUser);

        if(newToken==null){
            token = UUID.randomUUID().toString();
            newToken = new Token().builder()
                    .token(token)
                    .user(registeredUser)
                    .createdAt(LocalDateTime.now())
                    .expiredAt(LocalDateTime.now().plusMinutes(15))
                    .build();
            tokenRepo.save(newToken);
        }
        String link = "http://localhost:8080/register/" + token;
        emailSender.sendEmail(registeredUser.getEmail(), buildEmail(registeredUser.getName(), link));
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
        Roles role = rolesRepo.findByRoleName("USER");

        User newUser = new User().builder()
                .createdAt(LocalDateTime.now())
                .email(registeredUser.getEmail())
                .name(registeredUser.getEmail())
                .password(registeredUser.getPassword())
                .roles(role)
                .build();
        userRepo.save(newUser);
    }

    @Override
    public String resetPassword(String email) {
        if (email == null || email.isEmpty()) {
            throw new CustomizedException("Error", "email cannot be empty");
        }

        Optional<Register> userExists = registerRepo.findByEmail(email);
        if (!userExists.isPresent()) {
            throw new CustomizedException("Message", "User doesn't exists!");
        }

        final String token = UUID.randomUUID().toString();
        tokenRepo.save(new Token().toBuilder()
                .token(token)
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusMinutes(15))
                .user(userExists.get())
                .build());
        return token;
    }

    @Override
    public void resetPassword(String token, String password, String confirmPassword) {
        if (token == null || token.isEmpty()) {
            throw new CustomizedException("Token", "Token cannot be Empty!");
        }
        if (token == null || token.isEmpty()
                || password == null || password.isEmpty()
                || confirmPassword == null || confirmPassword.isEmpty()) {
            throw new CustomizedException("Message", "Password cannot be empty! ");
        }

        if (!password.equals(confirmPassword)) {
            throw new CustomizedException("Message", "Oops !! password aren't matching !!");
        }

        Optional<Token> foundToken = tokenRepo.findByToken(token);
        if (!foundToken.isPresent()) {
            throw new CustomizedException("Token", "Invalid token! ");
        }

        if (LocalDateTime.now().isAfter(foundToken.get().getExpiredAt())) {
            throw new CustomizedException("Token", "Token expired, kindly reset again!");
        }

        Register updateUser = foundToken.get().getUser();
        updateUser.setPassword(passwordEncoder.encode(password));
        registerRepo.save(updateUser);
    }

    private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }
}
