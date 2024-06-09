package com.example.travelly.Configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import org.modelmapper.ModelMapper;

@EnableWebSecurity
@Configuration
public class SecurityCongif {

    @Bean
    public SecurityFilterChain defaultSecurityFilterChains(HttpSecurity httpSecurity)throws Exception{
            httpSecurity.csrf((ele)->ele.disable())
                    .authorizeHttpRequests()
                    .requestMatchers("/register/**").permitAll();
//                    .requestMatchers("/login").authenticated();
           return httpSecurity.build();
    }



    @Bean
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }

    @Bean
    public ModelMapper modelMapper(){
        return  new ModelMapper();
    }

}
