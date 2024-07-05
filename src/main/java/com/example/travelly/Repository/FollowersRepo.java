package com.example.travelly.Repository;

import com.example.travelly.Model.Followers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FollowersRepo extends JpaRepository<Followers, Integer> {
    @Query(
            value = "SELECT id FROM followers f where f.user_id = ?1 and f.following_id = ?2",
            nativeQuery = true
    )
    Integer findByCurrentUserIdAndFollowingId(Integer userId, Integer followingId);
}
