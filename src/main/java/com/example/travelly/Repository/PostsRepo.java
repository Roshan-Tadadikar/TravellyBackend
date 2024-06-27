package com.example.travelly.Repository;

import com.example.travelly.Model.Posts;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostsRepo extends JpaRepository<Posts, Integer> {

    List<Posts> findAllByUserId(@Param("userId")Integer userId, Pageable pageable);
}
