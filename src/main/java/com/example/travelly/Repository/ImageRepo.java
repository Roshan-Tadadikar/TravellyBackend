package com.example.travelly.Repository;

import com.example.travelly.Model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepo extends JpaRepository<Image, Long> {
}
