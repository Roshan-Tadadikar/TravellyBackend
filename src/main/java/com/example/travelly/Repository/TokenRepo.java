package com.example.travelly.Repository;

import com.example.travelly.Model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepo extends JpaRepository<Token, Integer> {

    Token findByUserId(Integer userId);
    Optional<Token> findByToken(String token);
}
