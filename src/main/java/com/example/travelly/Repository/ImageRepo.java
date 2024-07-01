package com.example.travelly.Repository;

import com.example.travelly.Model.Image;
import com.example.travelly.Model.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface ImageRepo extends JpaRepository<Image, Long> {
    Set<Image> findByPosts(Posts post);
}
