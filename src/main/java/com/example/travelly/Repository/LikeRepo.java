package com.example.travelly.Repository;

import com.example.travelly.Model.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LikeRepo extends JpaRepository<Likes, Integer> {
    @Query(
            nativeQuery = true,
            value = "SELECT * FROM likes l where l.user_id= ?1 and l.post_id = ?2"
    )
    Optional<Likes> findByUsersAndPosts(Integer userId, Integer postId);
}
