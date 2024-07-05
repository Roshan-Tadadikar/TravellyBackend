package com.example.travelly.Repository;

import com.example.travelly.Model.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comments, Integer> {
}
