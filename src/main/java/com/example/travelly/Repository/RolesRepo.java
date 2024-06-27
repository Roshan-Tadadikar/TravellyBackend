package com.example.travelly.Repository;

import com.example.travelly.Model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepo extends JpaRepository<Roles, Integer> {
    Roles findByRoleName(String roleName);
}
