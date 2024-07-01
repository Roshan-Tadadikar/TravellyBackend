package com.example.travelly.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Likes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer likeId;
    private LocalDateTime addedTime;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User users;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Posts posts;
}
