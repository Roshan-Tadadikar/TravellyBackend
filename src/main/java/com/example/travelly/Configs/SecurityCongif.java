package com.example.travelly.Configs;

import com.example.travelly.Service.CustomUserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import org.modelmapper.ModelMapper;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@EnableWebSecurity
@Configuration
public class SecurityCongif{

    @Autowired
    CustomUserDetailsServiceImpl customUserDetailsService;

    @Bean
    public SecurityFilterChain defaultSecurityFilterChains(HttpSecurity httpSecurity)throws Exception{
            httpSecurity.csrf((ele)->ele.disable())
                    .authorizeHttpRequests((request)->
                            request.requestMatchers("/register/**").permitAll()
                                    .requestMatchers("/login").permitAll()
                                    .requestMatchers("/home").authenticated())
                    .formLogin((loginConfigurer)->loginConfigurer.loginPage("/login").defaultSuccessUrl("/home")
                            .failureUrl("/login").permitAll())
                    .logout(logOutConfigurer->logOutConfigurer.logoutSuccessUrl("/login").
                            invalidateHttpSession(true).permitAll());

           return httpSecurity.build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
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
