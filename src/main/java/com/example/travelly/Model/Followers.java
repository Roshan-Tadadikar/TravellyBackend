package com.example.travelly.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Table(name = "followers")
public class Followers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    @ManyToOne
    @JoinColumn(name = "follower_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "following_id")
    private User following;

}
