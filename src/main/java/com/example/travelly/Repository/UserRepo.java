package com.example.travelly.Repository;

import com.example.travelly.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Integer> {

     Optional<User> findByEmail(String email);


}
