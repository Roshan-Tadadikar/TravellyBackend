package com.example.travelly.Repository;

import com.example.travelly.Model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Integer> {

     Optional<User> findByEmail(String email);

     @Query("Select u from User u order by id desc")
     List<User> findAllLatestUsers(Pageable pageable);

}
