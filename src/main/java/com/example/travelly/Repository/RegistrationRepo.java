package com.example.travelly.Repository;

import com.example.travelly.Model.Register;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegistrationRepo extends JpaRepository<Register, Integer> {

    Optional<Register> findByEmail(String email);

}
